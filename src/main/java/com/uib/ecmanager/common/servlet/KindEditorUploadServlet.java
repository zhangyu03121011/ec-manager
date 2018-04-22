package com.uib.ecmanager.common.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.common.util.StringUtil;
import com.uib.ecmanager.common.config.Global;
import com.uib.ecmanager.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.uib.ecmanager.modules.sys.utils.UserUtils;

/**
 * KindEditor处理上传文件的servlet
 * 上传文件采用fastdfs client和程序自行保存2种方式
 * @author chengw
 *
 */
public class KindEditorUploadServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = LoggerFactory.getLogger(KindEditorUploadServlet.class);
	
	//最大文件大小
	private static long maxSize = 10000000;
			
	//定义允许上传的文件扩展名
	private static HashMap<String, String> extMap = new HashMap<String, String>();
	
	//fdfs client
	private static StorageClient client;
	
	static {
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("flash", "swf,flv");
		extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
	}
	
	
	//加载servlet时，实例化StorageClient
	static{
		try {
			ClientGlobal.init("/fdfs_client.properties");
		} catch (Exception me) {
			logger.info("fastdfs 初始化参数失败" + me);
		}
		TrackerClient tracker = new TrackerClient();
        TrackerServer trackerServer;
		try {
			trackerServer = tracker.getConnection();
			StorageServer storageServer = null;
	        client = new StorageClient(trackerServer, storageServer);
		} catch (IOException e) {
			logger.info("获取fastdfs client实例失败" + e);
		}
        
	}
	
	/**
	 * 采用fastdfs 客户端保存文件
	 * @param req
	 * @param resp
	 */
	public void fileUploadStreamByfdfs(HttpServletRequest req, HttpServletResponse resp){
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter out = null;
		try {
			out = resp.getWriter();
			
			Principal principal = (Principal) UserUtils.getPrincipal();
			if (principal == null){
				out.append(getError("请登录。"));
				return;
			}
			
			if(!ServletFileUpload.isMultipartContent(req)){
				out.append(getError("请选择文件。"));
				return;
			}
			
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("UTF-8");
			List<FileItem> items = null;
			try {
				items = upload.parseRequest(req);
			} catch (FileUploadException fe) {
				out.append(getError("解析上传文件异常"));
				return;
			}
			Iterator<FileItem> itr = items.iterator();
			
			while (itr.hasNext()) {
				FileItem item = itr.next();
				String fileName = item.getName();
				if (!item.isFormField()) {
					//检查文件大小
					if(item.getSize() > maxSize){
						out.append(getError("上传文件大小超过限制。"));
						return;
					}
					
					String dir = req.getParameter("dir");
					
					if(StringUtil.isNullOrEmpty(dir)){
						out.append(getError("请求参数[dir]为空。"));
						return;
					}
					
					//检查扩展名
					String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
					if(!Arrays.<String>asList(extMap.get(dir).split(",")).contains(fileExt)){
						out.append(getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dir) + "格式。"));
						return;
					}
					
					try {
				        NameValuePair[] meta_list = new NameValuePair[1];
				  		meta_list[0] = new NameValuePair("author", "fastdfs");
						String fileIds[] = client.upload_file(IOUtils.toByteArray(item.getInputStream()), fileExt, meta_list);
						String groupName = fileIds[0];
						String filePath = fileIds[1];
						String fdfsService = Global.getConfig("frontWeb.image.baseUrl");
						String imageUrl = fdfsService + "/" + groupName + "/" + filePath;
						JSONObject obj = new JSONObject();
						obj.put("error", 0);
						obj.put("url", imageUrl);
						out.append(obj.toJSONString());
						
						logger.info("return object to json string=" + obj.toJSONString());
			  		} catch (MyException e) {
						out.append(getError("fastdfs 上传文件失败"));
						return;
					}
				}
			}
			
		} catch (IOException ie) {
			logger.error("fastdfs 上传文件失败:" + ie);
			out.close();
		} 
	}
	
	/**
	 * 程序在服务器上自己创建目录保存文件
	 * @param req
	 * @param resp
	 */
	public void fileUploadStream(HttpServletRequest req, HttpServletResponse resp){
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter out = null;
		try {
			out = resp.getWriter();
			//登录校验
			Principal principal = (Principal) UserUtils.getPrincipal();
			if (principal == null){
				out.append(getError("请登录。"));
				return;
			}
		} catch (IOException e1) {
			logger.error("上传文件失败:" + e1);
		}
		if(!ServletFileUpload.isMultipartContent(req)){
			out.println(getError("请选择文件。"));
			return;
		}
		String savePath = Global.getConfig("upload.image.path");
		String frontWebUrl = Global.getConfig("frontWeb.image.baseUrl");
		String filefolder = Global.getConfig("frontweb.image.folder");
		frontWebUrl =  frontWebUrl + filefolder;
		String saveUrl = "";
		//检查目录
		File uploadDir = new File(savePath);
		if(!uploadDir.isDirectory()){
			out.println(getError("上传目录不存在。"));
			return;
		}
		//检查目录写权限
		if(!uploadDir.canWrite()){
			out.println(getError("上传目录没有写权限。"));
			return;
		}

		String dirName = req.getParameter("dir");
		if (dirName == null) {
			dirName = "image";
		}
		if(!extMap.containsKey(dirName)){
			out.println(getError("目录名不正确。"));
			return;
		}
		//创建文件夹
		savePath += dirName + "/";
		saveUrl += dirName + "/";

		File saveDirFile = new File(savePath);
		if (!saveDirFile.exists()) {
			saveDirFile.mkdirs();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String ymd = sdf.format(new Date());
		savePath += ymd + "/";
		saveUrl += ymd + "/";
		File dirFile = new File(savePath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}

		logger.info("savePath111==" + savePath + ",saveUrl111==" + saveUrl);

		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		List<FileItem> items = null;
		try {
			items = upload.parseRequest(req);
			Iterator<FileItem> itr = items.iterator();
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				String fileName = item.getName();
				//long fileSize = item.getSize();
				if (!item.isFormField()) {
					//检查文件大小
					if(item.getSize() > maxSize){
						out.println(getError("上传文件大小超过限制。"));
						return;
					}
					//检查扩展名
					String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
					if(!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt)){
						out.println(getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。"));
						return;
					}

					SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
					String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
					try{
						File uploadedFile = new File(savePath, newFileName);
						item.write(uploadedFile);
					}catch(Exception e){
						logger.error("上传文件异常:"+ e);
						out.println(getError("上传文件失败。"));
						return;
					}
					String linkImageUrl =	frontWebUrl +saveUrl+ newFileName;
					JSONObject obj = new JSONObject();
					obj.put("error", 0);
					obj.put("url", linkImageUrl);
					out.println(obj.toJSONString());
					logger.info("obj.tojsonstring====" + obj.toJSONString());
				}
			}
		} catch (FileUploadException e2) {
			logger.error("上传文件异常:"+ e2);
			out.append(getError("上传文件失败"));
		}
		
	}
	
	private String getError(String message) {
		JSONObject obj = new JSONObject();
		obj.put("error", 1);
		obj.put("message", message);
		return obj.toJSONString();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String isFdfs = Global.getConfig("isFastdfsImageService");
		if(Boolean.parseBoolean(isFdfs)){
			fileUploadStreamByfdfs(req, resp);
		}else{
			fileUploadStream(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String isFdfs = Global.getConfig("isFastfdsImageService");
		if(Boolean.parseBoolean(isFdfs)){
			fileUploadStreamByfdfs(req, resp);
		}else{
			fileUploadStream(req, resp);
		}
	}
}
