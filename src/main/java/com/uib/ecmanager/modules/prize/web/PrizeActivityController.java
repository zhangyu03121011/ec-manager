/**
 * Copyright &copy; 2012-2014 <a href="http://www.easypay.com.hk/basicframework">basicframework</a> All rights reserved.
 */
package com.uib.ecmanager.modules.prize.web;

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

import com.uib.ecmanager.common.config.Global;
import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.utils.StringUtils;
import com.uib.ecmanager.common.web.BaseController;
import com.uib.ecmanager.modules.prize.entity.PrizeActivity;
import com.uib.ecmanager.modules.prize.service.PrizeActivityService;
import com.uib.ecmanager.modules.sys.entity.User;
import com.uib.ecmanager.modules.sys.utils.UserUtils;

/**
 * 抽奖活动Controller
 * @author gyq
 * @version 2017-01-21
 */
@Controller
@RequestMapping(value = "${adminPath}/prize/prizeActivity")
public class PrizeActivityController extends BaseController {

	@Autowired
	private PrizeActivityService prizeActivityService;
	
	@ModelAttribute
	public PrizeActivity get(@RequestParam(required=false) String id) {
		PrizeActivity entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = prizeActivityService.get(id);
		}
		if (entity == null){
			entity = new PrizeActivity();
		}
		return entity;
	}
	
	@RequiresPermissions("prize:prizeActivity:view")
	@RequestMapping(value = {"list", ""})
	public String list(PrizeActivity prizeActivity, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PrizeActivity> page = prizeActivityService.findPage(new Page<PrizeActivity>(request, response), prizeActivity); 
		model.addAttribute("page", page);
		return "modules/prize/prizeActivityList";
	}

	@RequiresPermissions("prize:prizeActivity:view")
	@RequestMapping(value = "form")
	public String form(PrizeActivity prizeActivity, Model model) {
		model.addAttribute("prizeActivity", prizeActivity);
		return "modules/prize/prizeActivityForm";
	}

	@RequiresPermissions("prize:prizeActivity:edit")
	@RequestMapping(value = "save")
	public String save(PrizeActivity prizeActivity, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, prizeActivity)){
			return form(prizeActivity, model);
		}
		User user=UserUtils.getUser();
		prizeActivity.setCreateBy(user);
		long startTime = prizeActivity.getStartTime().getTime();
		long endTime = prizeActivity.getEndTime().getTime();
		if(startTime>endTime){
			addMessage(redirectAttributes, "保存出错:开始时间不大于结束时间");	
			return "redirect:"+Global.getAdminPath()+"/prize/prizeActivity/?repage";
		}
		if(StringUtils.isNotBlank(prizeActivity.getId())){
			prizeActivity.setUpdateBy(user);
			prizeActivityService.update(prizeActivity);
			addMessage(redirectAttributes, "修改抽奖活动信息成功");
		}else{
			prizeActivityService.save(prizeActivity);
			addMessage(redirectAttributes, "保存抽奖活动信息成功");	
		}
		return "redirect:"+Global.getAdminPath()+"/prize/prizeActivity/?repage";
	}
	
	@RequiresPermissions("prize:prizeActivity:edit")
	@RequestMapping(value = "delete")
	public String delete(PrizeActivity prizeActivity, RedirectAttributes redirectAttributes) {
		prizeActivityService.delete(prizeActivity);
		addMessage(redirectAttributes, "删除抽奖活动信息成功");
		return "redirect:"+Global.getAdminPath()+"/prize/prizeActivity/?repage";
	}

}