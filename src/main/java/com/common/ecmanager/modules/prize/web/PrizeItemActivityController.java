/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.common.ecmanager.modules.prize.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.common.ecmanager.modules.prize.entity.PrizeItemActivity;
import com.common.ecmanager.modules.prize.service.PrizeItemActivityService;
import com.common.ecmanager.modules.sys.entity.User;
import com.common.ecmanager.modules.sys.utils.UserUtils;

/**
 * 抽奖活动副表Controller
 * @author luoxf
 * @version 2017-01-22
 */
@Controller
@RequestMapping(value = "${adminPath}/prize/prizeItemActivity")
public class PrizeItemActivityController extends BaseController {

	@Autowired
	private PrizeItemActivityService prizeItemActivityService;
	
	@ModelAttribute
	public PrizeItemActivity get(@RequestParam(required=false) String id) {
		PrizeItemActivity entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = prizeItemActivityService.get(id);
		}
		if (entity == null){
			entity = new PrizeItemActivity();
		}
		return entity;
	}
	
	
	@RequestMapping(value = {"list", ""})
	public String list(PrizeItemActivity prizeItemActivity, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PrizeItemActivity> page = prizeItemActivityService.findPage(new Page<PrizeItemActivity>(request, response), prizeItemActivity); 
		model.addAttribute("page", page);
		return "modules/prize/prizeItemActivityList";
	}

	
	@RequestMapping(value = "form")
	public String form(PrizeItemActivity prizeItemActivity, Model model) {
		model.addAttribute("prizeItemActivity", prizeItemActivity);
		return "modules/prize/prizeItemActivityForm";
	}
	
	
	@RequestMapping(value = "updateFormView")
	public String updateFormView(PrizeItemActivity prizeItemActivity, Model model) {
		model.addAttribute("prizeItemActivity", prizeItemActivity);
		return "modules/prize/prizeItemActivityupdateForm";
	}

	
	@RequestMapping(value = "save")
	public String save(PrizeItemActivity prizeItemActivity, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, prizeItemActivity)){
			return form(prizeItemActivity, model);
		}
		User user=UserUtils.getUser();
		prizeItemActivity.setCreateBy(user);
		if(StringUtils.isNotBlank(prizeItemActivity.getId())){
			prizeItemActivity.setUpdateBy(user);
			prizeItemActivityService.update(prizeItemActivity);
			addMessage(redirectAttributes, "修改抽奖活动副表成功");
		}else{
			prizeItemActivityService.save(prizeItemActivity);
			addMessage(redirectAttributes, "保存抽奖活动副表成功");	
		}
		addMessage(redirectAttributes, "保存抽奖活动副表成功");
		return "redirect:"+Global.getAdminPath()+"/prize/prizeItemActivity/?repage";
	}
	
	
	@RequestMapping(value = "update")
	public String update(PrizeItemActivity prizeItemActivity, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, prizeItemActivity)){
			return form(prizeItemActivity, model);
		}
		prizeItemActivityService.update(prizeItemActivity);
		addMessage(redirectAttributes, "修改抽奖活动副表成功");
		return "redirect:"+Global.getAdminPath()+"/prize/prizeItemActivity/?repage";
	}
	
	
	@RequestMapping(value = "delete")
	public String delete(PrizeItemActivity prizeItemActivity, RedirectAttributes redirectAttributes) {
		prizeItemActivityService.delete(prizeItemActivity);
		addMessage(redirectAttributes, "删除抽奖活动副表成功");
		return "redirect:"+Global.getAdminPath()+"/prize/prizeItemActivity/?repage";
	}

}