package com.uib.ecmanager.modules.mem.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uib.ecmanager.common.service.CrudService;
import com.uib.ecmanager.common.utils.StringUtils;
import com.uib.ecmanager.modules.mem.dao.RecommenderLogDao;
import com.uib.ecmanager.modules.mem.entity.RecommenderLog;

@Service
public class RecommenderInterfaceService extends CrudService<RecommenderLogDao, RecommenderLog> {
	private Logger logger = LoggerFactory.getLogger(RecommenderInterfaceService.class); 
	
	@Autowired
	private RecommenderLogDao recommenderLogDao;
	
	@Autowired
	private MemMemberService memMemberService;
	
	public Map<String,Integer> addClickAndLink(RecommenderLog recommenderLog){
		Map<String,Integer> map = new HashMap<String, Integer>();
		logger.info("会员MemberId:"+recommenderLog.getMemberId()+"---产品Id:"+recommenderLog.getProductId());
		if(StringUtils.isNotBlank(recommenderLog.getMemberId())&&StringUtils.isNotBlank(recommenderLog.getProductId())){
			map.put("status",1);
			List<RecommenderLog> recommenderLogList = recommenderLogDao.findList(recommenderLog);
			//计算点击数和已关注数
			RecommenderLog recomm =new RecommenderLog();
			recomm.setParentId(recommenderLog.getMemberId()+"-"+recommenderLog.getProductId());
			for(RecommenderLog r:recommenderLogList){
				String[] ids = r.getParentIds().split(",");
				//遍历parentIds,查找下一级
				for(String id:ids){
					String pp = id+"-"+r.getProductId();
					if(pp.equals(recomm.getParentId())){
						//判断下一级是否点击还是已关注
						if("1".equals(r.getMemberStatusCode())){
							int n = recomm.getClickCount()+1;
							map.put("click",n);
							recomm.setClickCount(n);
						}else if("2".equals(r.getMemberStatusCode())){
							int m = recomm.getLinkCount()+1;
							map.put("link", m);
							recomm.setLinkCount(m);
						}
					}
				}
			}
		}else{
			map.put("status",0);
			logger.info("分享记录计算点击已关注数查询错误：memberId和productId不能为空");
		}
		return map;
	}
}
