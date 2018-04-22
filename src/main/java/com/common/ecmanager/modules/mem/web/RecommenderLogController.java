/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.common.ecmanager.modules.mem.web;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.common.ecmanager.common.config.Global;
import com.common.ecmanager.common.persistence.Page;
import com.common.ecmanager.common.utils.StringUtils;
import com.common.ecmanager.common.web.BaseController;
import com.common.ecmanager.modules.mem.entity.MemMember;
import com.common.ecmanager.modules.mem.entity.RecommenderLog;
import com.common.ecmanager.modules.mem.service.MemMemberService;
import com.common.ecmanager.modules.mem.service.RecommenderLogService;
import com.common.ecmanager.modules.product.service.ProductService;

/**
 * 推广记录Controller
 * @author heyh
 * @version 2017-01-03
 */
@Controller
@RequestMapping(value = "${adminPath}/mem/recommenderLog")
public class RecommenderLogController extends BaseController {

	@Autowired
	private RecommenderLogService recommenderLogService;
	
	@Autowired
	private MemMemberService memMemberService;
	
	@Autowired
	private ProductService productService;
	
	@ModelAttribute
	public RecommenderLog get(@RequestParam(required=false) String id) {
		RecommenderLog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = recommenderLogService.get(id);
		}
		if (entity == null){
			entity = new RecommenderLog();
		}
		return entity;
	}
	
	@RequiresPermissions("mem:recommenderLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(RecommenderLog recommenderLog, String sortId,HttpServletRequest request, HttpServletResponse response, Model model) {
		final String sort = sortId;
		if(StringUtils.isNotBlank(recommenderLog.getMemMemberName())){
			MemMember memMember2 = new MemMember();
			memMember2.setName(recommenderLog.getMemMemberName());
			MemMember memMember3 = memMemberService.getMemMemberByName(memMember2);
			if(memMember3!=null){
				recommenderLog.setMemberId(memMember3.getId());
				Page<RecommenderLog> page = recommenderLogService.findPage(new Page<RecommenderLog>(request, response), recommenderLog);
				List<RecommenderLog> recommenderLogList = page.getList();
				if("2".equals(sort)){
					Collections.sort(recommenderLogList,new Comparator<RecommenderLog>() {
						@Override
						public int compare(RecommenderLog o1, RecommenderLog o2) {
							if((o1.getClickCount())>(o2.getClickCount())){
								return -1;
							}else if((o1.getClickCount())==(o2.getClickCount())){
								return 0;
							}else{
								return 1;
							}
						}
					});
				}else{
					Collections.sort(recommenderLogList,new Comparator<RecommenderLog>() {
						@Override
						public int compare(RecommenderLog o1, RecommenderLog o2) {
							if((o1.getLinkCount())>(o2.getLinkCount())){
								return -1;
							}else if((o1.getLinkCount())==(o2.getLinkCount())){
								return 0;
							}else{
								return 1;
							}
						}
					});
				}
				page.setList(recommenderLogList);
				model.addAttribute("page",page);
				model.addAttribute("list",recommenderLogList);
				model.addAttribute("recommenderLog", recommenderLog);
			}
		}else{
			Page<RecommenderLog> page = recommenderLogService.findPage(new Page<RecommenderLog>(request, response), recommenderLog);
			List<RecommenderLog> recommenderLogList = page.getList();
			if("2".equals(sort)){
				Collections.sort(recommenderLogList,new Comparator<RecommenderLog>() {
					@Override
					public int compare(RecommenderLog o1, RecommenderLog o2) {
						if((o1.getClickCount())>(o2.getClickCount())){
							return -1;
						}else if((o1.getClickCount())==(o2.getClickCount())){
							return 0;
						}else{
							return 1;
						}
					}
				});
			}else{
				Collections.sort(recommenderLogList,new Comparator<RecommenderLog>() {
					@Override
					public int compare(RecommenderLog o1, RecommenderLog o2) {
						if((o1.getLinkCount())>(o2.getLinkCount())){
							return -1;
						}else if((o1.getLinkCount())==(o2.getLinkCount())){
							return 0;
						}else{
							return 1;
						}
					}
				});
			}
			page.setList(recommenderLogList);
			model.addAttribute("page",page);
			model.addAttribute("list",recommenderLogList);
			model.addAttribute("recommenderLog", recommenderLog);
		}
		return "modules/mem/recommenderLogList";
//		for(RecommenderLog recomm :recommenderLogList){
//			String parentId = recomm.getParentId().split("-")[0];
//			String memberId = recomm.getMemberId().split("-")[0];
//			
//			//查找推广人和被推广人
//			MemMember mem = new MemMember();
//			mem.setId(parentId);
//			MemMember parentMemMember = memMemberService.get(mem);
//			if(parentMemMember!=null){
//				recomm.setParentName(parentMemMember.getName());
//			}
//			mem.setId(memberId);
//			MemMember memMember = memMemberService.get(mem);
//			if(memMember!=null){
//				recomm.setMemMemberName(memMember.getName());
//			}
//			
//			//查找商品名称和商品编码
//			Product product = new Product();
//			product.setId(recomm.getProductId());
//			Product productFind = productService.get(product);
//			if(productFind!=null){
//				recomm.setProductCode(productFind.getProductNo());
//				recomm.setProductName(productFind.getFullName());
//			}
//			
//			//计算点击数和已关注数
//			for(RecommenderLog r:recommenderLogList){
//				String[] ids = r.getParentIds().split(",");
//				for(String id:ids){
//					String pp = id+"-"+r.getProductId();
//					if(pp.equals(recomm.getParentId())){
//						if("1".equals(r.getMemberStatusCode())){
//							int n = recomm.getClickCount()+1;
//							recomm.setClickCount(n);
//						}else if("2".equals(r.getMemberStatusCode())){
//							int m = recomm.getLinkCount()+1;
//							recomm.setLinkCount(m);
//						}
//					}
//				}
//			}
//			
			
//			if(ParentIdMap.get(recomm.getParentId())==null){
//				if(StringUtils.isNotBlank(recomm.getParentId())){
//					if("1".equals(recomm.getMemberStatusCode())){
//						int n = recomm.getClickCount()+1;
//						recomm.setClickCount(n);
//					}else if("2".equals(recomm.getMemberStatusCode())){
//						int m = recomm.getLinkCount()+1;
//						recomm.setLinkCount(m);
//					}
//				}
//			}else{
//				RecommenderLog recommen = ParentIdMap.get(recomm.getParentId());
//				if(StringUtils.isNotBlank(recomm.getParentId())){
//					if("1".equals(recomm.getMemberStatusCode())){
//						int n = recomm.getClickCount()+1+recommen.getClickCount();
//						recomm.setClickCount(n);
//						recomm.setLinkCount(recommen.getLinkCount()+recomm.getLinkCount());
//					}else if("2".equals(recomm.getMemberStatusCode())){
//						int m = recomm.getLinkCount()+1+recommen.getLinkCount();
//						recomm.setClickCount(recommen.getClickCount()+recomm.getClickCount());
//						recomm.setLinkCount(m);
//					}
//				}
//			}
//			ParentIdMap.put(recomm.getParentId(), recomm);
//			addMemberStatus(recomm,recommenderLogList);
//		}
//		if(ParentIdMap!=null&&ParentIdMap.size()>0){
//			page.setCount(ParentIdMap.size());
//		}
		
	}

//	private void addMemberStatus(RecommenderLog rec,List<RecommenderLog> recommenderLogList){
//		for(RecommenderLog recomm :recommenderLogList){
//			if(rec.getMemberId().equals(recomm.getParentId())){
//				if("1".equals(recomm.getMemberStatusCode())){
//					int c = rec.getClickCount()+1;
//					rec.setClickCount(c);
//				}else if("2".equals(recomm.getMemberStatusCode())){
//					int l = rec.getLinkCount()+1;
//					rec.setLinkCount(l);
//				}
//				addMemberStatus(recomm,recommenderLogList);
//			}
//		}
//	}
	
	
	@RequiresPermissions("mem:recommenderLog:view")
	@RequestMapping(value = "form")
	public String form(RecommenderLog recommenderLog, Model model) {
		model.addAttribute("recommenderLog", recommenderLog);
		return "modules/mem/recommenderLogForm";
	}
	
	@RequiresPermissions("mem:recommenderLog:view")
	@RequestMapping(value = "updateFormView")
	public String updateFormView(RecommenderLog recommenderLog, Model model) {
		model.addAttribute("recommenderLog", recommenderLog);
		return "modules/mem/recommenderLogupdateForm";
	}

	@RequiresPermissions("mem:recommenderLog:save")
	@RequestMapping(value = "save")
	public String save(RecommenderLog recommenderLog, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, recommenderLog)){
			return form(recommenderLog, model);
		}
		recommenderLogService.save(recommenderLog);
		addMessage(redirectAttributes, "保存推广记录成功");
		return "redirect:"+Global.getAdminPath()+"/mem/recommenderLog/?repage";
	}
	
	@RequiresPermissions("mem:recommenderLog:edit")
	@RequestMapping(value = "update")
	public String update(RecommenderLog recommenderLog, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, recommenderLog)){
			return form(recommenderLog, model);
		}
		recommenderLogService.update(recommenderLog);
		addMessage(redirectAttributes, "修改推广记录成功");
		return "redirect:"+Global.getAdminPath()+"/mem/recommenderLog/?repage";
	}
	
	@RequiresPermissions("mem:recommenderLog:edit")
	@RequestMapping(value = "delete")
	public String delete(RecommenderLog recommenderLog, RedirectAttributes redirectAttributes) {
		recommenderLogService.delete(recommenderLog);
		addMessage(redirectAttributes, "删除推广记录成功");
		return "redirect:"+Global.getAdminPath()+"/mem/recommenderLog/?repage";
	}
	
	
}