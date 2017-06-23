/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.mem.web;

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
import com.uib.ecmanager.modules.mem.entity.MemMember;
import com.uib.ecmanager.modules.mem.entity.PrizeRecommender;
import com.uib.ecmanager.modules.mem.service.MemMemberService;
import com.uib.ecmanager.modules.mem.service.PrizeRecommenderService;

/**
 * 中奖记录Controller
 * @author heyh
 * @version 2017-01-21
 */
@Controller
@RequestMapping(value = "${adminPath}/mem/prizeRecommender")
public class PrizeRecommenderController extends BaseController {

	@Autowired
	private PrizeRecommenderService prizeRecommenderService;
	
	@Autowired
	private MemMemberService memMemberService;
	
	@ModelAttribute
	public PrizeRecommender get(@RequestParam(required=false) String id) {
		PrizeRecommender entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = prizeRecommenderService.get(id);
		}
		if (entity == null){
			entity = new PrizeRecommender();
		}
		return entity;
	}
	
//	@RequiresPermissions("mem:prizeRecommender:view")
	@RequestMapping(value = {"list", ""})
	public String list(PrizeRecommender prizeRecommender, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(StringUtils.isNotBlank(prizeRecommender.getMemMemberName())){
			MemMember memMember = new MemMember();
			memMember.setUsername(prizeRecommender.getMemMemberName());
			memMember = memMemberService.getMemMemberByName(memMember);
			prizeRecommender.setMember(memMember);
		}
		Page<PrizeRecommender> page = prizeRecommenderService.findPage(new Page<PrizeRecommender>(request, response), prizeRecommender); 
		model.addAttribute("page", page);
		model.addAttribute("prizeRecommender", prizeRecommender);
		return "modules/mem/prizeRecommenderList";
	}

	@RequiresPermissions("mem:prizeRecommender:view")
	@RequestMapping(value = "form")
	public String form(PrizeRecommender prizeRecommender, Model model) {
		model.addAttribute("prizeRecommender", prizeRecommender);
		return "modules/mem/prizeRecommenderForm";
	}
	
	@RequiresPermissions("mem:prizeRecommender:view")
	@RequestMapping(value = "updateFormView")
	public String updateFormView(PrizeRecommender prizeRecommender, Model model) {
		model.addAttribute("prizeRecommender", prizeRecommender);
		return "modules/mem/prizeRecommenderupdateForm";
	}

	@RequiresPermissions("mem:prizeRecommender:save")
	@RequestMapping(value = "save")
	public String save(PrizeRecommender prizeRecommender, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, prizeRecommender)){
			return form(prizeRecommender, model);
		}
		prizeRecommenderService.save(prizeRecommender);
		addMessage(redirectAttributes, "保存中奖记录成功");
		return "redirect:"+Global.getAdminPath()+"/mem/prizeRecommender/?repage";
	}
	
	@RequiresPermissions("mem:prizeRecommender:edit")
	@RequestMapping(value = "update")
	public String update(PrizeRecommender prizeRecommender, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, prizeRecommender)){
			return form(prizeRecommender, model);
		}
		prizeRecommenderService.update(prizeRecommender);
		addMessage(redirectAttributes, "修改中奖记录成功");
		return "redirect:"+Global.getAdminPath()+"/mem/prizeRecommender/?repage";
	}
	
	@RequiresPermissions("mem:prizeRecommender:edit")
	@RequestMapping(value = "delete")
	public String delete(PrizeRecommender prizeRecommender, RedirectAttributes redirectAttributes) {
		prizeRecommenderService.delete(prizeRecommender);
		addMessage(redirectAttributes, "删除中奖记录成功");
		return "redirect:"+Global.getAdminPath()+"/mem/prizeRecommender/?repage";
	}

}