/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.product.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.uib.ecmanager.common.config.Global;
import com.uib.ecmanager.common.enums.ActivityType;
import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.utils.ReturnMsg;
import com.uib.ecmanager.common.utils.StringUtils;
import com.uib.ecmanager.common.web.BaseController;
import com.uib.ecmanager.modules.product.entity.Product;
import com.uib.ecmanager.modules.product.entity.Special;
import com.uib.ecmanager.modules.product.entity.SpecialProductRef;
import com.uib.ecmanager.modules.product.service.ProductService;
import com.uib.ecmanager.modules.product.service.SpecialProductRefService;
import com.uib.ecmanager.modules.product.service.SpecialService;

/**
 * 专题信息Controller
 * @author limy
 * @version 2016-07-14
 */
@Controller
@RequestMapping(value = "${adminPath}/product/special")
public class SpecialController extends BaseController {

	@Autowired
	private SpecialService specialService;
	
	@Autowired
	private SpecialProductRefService specialProductRefService;
	
	@Autowired
	private ProductService productService;
	@ModelAttribute
	public Special get(@RequestParam(required=false) String id) {
		Special entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = specialService.get(id);
		}
		if (entity == null){
			entity = new Special();
		}
		return entity;
	}
	
	@RequiresPermissions("product:special:view")
	@RequestMapping(value = {"list", ""})
	public String list(Special special, HttpServletRequest request, HttpServletResponse response, Model model) {
//		special.setType("0");
		special.setType(ActivityType.special.getValue());
		Page<Special> page = specialService.findPage(new Page<Special>(request, response), special); 
		model.addAttribute("page", page);
		return "modules/product/specialList";
	}

	@RequiresPermissions("product:special:view")
	@RequestMapping(value = "form")
	//public String form(Special special, HttpServletRequest request,Model model) {
	public String form(Special special,Model model,RedirectAttributes redirectAttributes) {
		Product product = new Product();
		product.setSpecialId(special.getId());
		product.setUpdateFlag(special.getUpdateFlag());
		model.addAttribute("product", product);
		model.addAttribute("special", special);
		return "modules/product/specialForm";
	}
	
	@RequiresPermissions("product:special:view")
	@RequestMapping(value = "specialView")
	public String specialView(Special special, HttpServletRequest request,Model model) {
		Product product = new Product();
		product.setSpecialId(special.getId());
		product.setUpdateFlag(special.getUpdateFlag());
		model.addAttribute("product", product);
		model.addAttribute("special", special);
		return "modules/product/specialView";
	}
	
	@RequiresPermissions("product:special:view")
	@RequestMapping(value = "updateFormView")
	public String updateFormView(Special special,HttpServletRequest request, Model model) {
		model.addAttribute("special", special);
		Product product = new Product();
		product.setSpecialId(special.getId());
		product.setUpdateFlag(special.getUpdateFlag());
		model.addAttribute("product", product);
		model.addAttribute("special", special);
		return "modules/product/specialupdateForm";
	}

	@RequiresPermissions("product:special:save")
	@RequestMapping(value = "save")
	public String save(Special special, Model model,RedirectAttributes redirectAttributes) {
		/*if (!beanValidator(model, special)){
			return form(special, model);
		}*/
		if(special.getSort().equals("0")){
			addMessage(model, "排序字段不能为0");
			return form(special, model,redirectAttributes);
		}
		if(special.getBeginDate().after(special.getEndDate())){
			addMessage(model, "结束日期不能早于开始日期");
			return form(special, model,redirectAttributes);
		}
		special.setUpdateFlag("0");
//		special.setType("0");
		special.setType(ActivityType.special.getValue());
		specialService.save(special);
		addMessage(redirectAttributes, "添加专题信息成功");
		return "redirect:"+Global.getAdminPath()+"/product/special/productList?specialId="+special.getId()+"&updateFlag="+special.getUpdateFlag();
	}
	
	@RequiresPermissions("product:special:view")
	@RequestMapping(value = "formView")
	public String formView(Special special, HttpServletRequest request,Model model) {
		Product product = new Product();
		product.setSpecialId(special.getId());
		product.setUpdateFlag(special.getUpdateFlag());
		model.addAttribute("product", product);
		model.addAttribute("special", special);
		return "modules/product/specialFormView";
	}
	
	@RequiresPermissions("product:special:edit")
	@RequestMapping(value = "saveForm")
	public String saveFormView(Special special, Model model,RedirectAttributes redirectAttributes) {
		special.setUpdateFlag("2");
		specialService.update(special);
		addMessage(redirectAttributes, "修改专题信息成功");
		return "redirect:"+Global.getAdminPath()+"/product/special/productList?specialId="+special.getId()+"&updateFlag="+special.getUpdateFlag();
	}
	
	@RequiresPermissions("product:special:edit")
	@RequestMapping(value = "update")
	public String update(Special special, Model model,RedirectAttributes redirectAttributes) {
		if(special.getSort().equals("0")){
			addMessage(model, "排序字段不能为0");
			return form(special, model,redirectAttributes);
		}
		if(special.getBeginDate().after(special.getEndDate())){
			addMessage(model, "结束日期不能早于开始日期");
			return form(special, model,redirectAttributes);
		}
		special.setUpdateFlag("2");
		specialService.update(special);
		addMessage(redirectAttributes, "修改专题信息成功");
//		return "redirect:"+Global.getAdminPath()+"/product/special/updateProductList?specialId="+special.getId()+"&updateFlag="+special.getUpdateFlag();
		return "redirect:"+Global.getAdminPath()+"/product/special";
	}
	
	@RequiresPermissions("product:special:edit")
	@RequestMapping(value = "delete")
	public String delete(Special special, RedirectAttributes redirectAttributes) {
		Date nowTime = new Date();
		if(nowTime.after(special.getBeginDate())&& nowTime.before(special.getEndDate())){
			addMessage(redirectAttributes, "有效期内的专题不能删除！");
		}else{
			specialService.delete(special);
			addMessage(redirectAttributes, "删除专题信息成功");
		}
		
		return "redirect:"+Global.getAdminPath()+"/product/special/?repage";
	}

	@RequiresPermissions("product:special:edit")
	@RequestMapping(value = "selectProduct")
	public String selectProduct(SpecialProductRef specialProductRef, HttpServletRequest request, HttpServletResponse response, Model model) {
		Product product = new Product();
		product.setUpdateFlag(specialProductRef.getUpdateFlag());
		product.setSpecialId(specialProductRef.getSpecialId());
		if(specialProductRef.getSpecialId() != null ){
		Page<SpecialProductRef> page = specialProductRefService.findPage(new Page<SpecialProductRef>(request, response), specialProductRef); 
		model.addAttribute("page", page);
//		model.addAttribute("product", product);
		model.addAttribute("searchProductRef", specialProductRef);
		}
		return "modules/product/specialProductList";
	}
	
	@RequiresPermissions("product:special:edit")
	@RequestMapping(value = "updateSelectProduct")
	public String updateSelectProduct(SpecialProductRef specialProductRef, HttpServletRequest request, HttpServletResponse response, Model model) {
		Product product = new Product();
		product.setUpdateFlag(specialProductRef.getUpdateFlag());
		product.setSpecialId(specialProductRef.getSpecialId());
		if(specialProductRef.getSpecialId() != null ){
		Page<SpecialProductRef> page = specialProductRefService.findPage(new Page<SpecialProductRef>(request, response), specialProductRef); 
		model.addAttribute("page", page);
		model.addAttribute("product", product);
		model.addAttribute("searchProductRef", specialProductRef);
		}
		return "modules/product/updateSpecialProductList";
	}
	
	@RequiresPermissions("product:special:edit")
	@RequestMapping(value = "deleteSpecialProduct")
	public String deleteSpecialProduct(SpecialProductRef specialProductRef, RedirectAttributes redirectAttributes,Model model) {
		specialProductRefService.delete(specialProductRef);
		addMessage(redirectAttributes, "删除专题商品成功");
		return "redirect:"+Global.getAdminPath()+"/product/special/selectProduct?specialId="+specialProductRef.getSpecialId();
	}
	
	@RequiresPermissions("product:special:edit")
	@RequestMapping(value = "updateDeleteSpecialProduct")
	public String updateDeleteSpecialProduct(SpecialProductRef specialProductRef, RedirectAttributes redirectAttributes,Model model) {
		specialProductRefService.delete(specialProductRef);
		addMessage(redirectAttributes, "删除专题商品成功");
		return "redirect:"+Global.getAdminPath()+"/product/special/updateSelectProduct?specialId="+specialProductRef.getSpecialId();
	}

	@RequiresPermissions("product:special:view")
	@RequestMapping(value = "productList")
	public String productList(Product product,HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Product> page = productService.findPages(new Page<Product>(request, response), product);
		model.addAttribute("page", page);
		model.addAttribute("product", product);
		return "modules/product/specialProductAddList";
	}
	
	@RequiresPermissions("product:special:view")
	@RequestMapping(value = "updateProductList")
	public String updateProductList(Product product,HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Product> page = productService.findPages(new Page<Product>(request, response), product);
		model.addAttribute("page", page);
		model.addAttribute("product", product);
		return "modules/product/updateSpecialProductAddList";
	}

	@RequiresPermissions("product:special:edit")
 	@RequestMapping(value="addProduct")
 	public String addProduct(String[] productId,String specialId,String updateFlag,HttpServletRequest request,RedirectAttributes redirectAttributes,HttpServletResponse response, Model model){
 		if(productId.length>0 && !specialId.equals("")){
 		List<SpecialProductRef> sprList = new ArrayList<SpecialProductRef>();
 		for (String pid :productId ){
 			SpecialProductRef spr = new SpecialProductRef();
 			spr.setId(UUID.randomUUID().toString().replace("-", ""));
 			spr.setProductId(pid);
 			spr.setSpecialId(specialId);
 			sprList.add(spr);
 		}
			specialProductRefService.insertSpecialProductRef(sprList);
			addMessage(redirectAttributes, "添加专题商品成功");
		}
 		return "redirect:"+Global.getAdminPath()+"/product/special/selectProduct?specialId="+specialId+"&updateFlag="+updateFlag;
 	}
	
	@RequiresPermissions("product:special:edit")
 	@RequestMapping(value="updateAddProduct")
 	public String updateAddProduct(String[] productId,String specialId,String updateFlag,HttpServletRequest request,RedirectAttributes redirectAttributes,HttpServletResponse response, Model model){
 		if(productId.length>0 && !specialId.equals("")){
 		List<SpecialProductRef> sprList = new ArrayList<SpecialProductRef>();
 		for (String pid :productId ){
 			SpecialProductRef spr = new SpecialProductRef();
 			spr.setId(UUID.randomUUID().toString().replace("-", ""));
 			spr.setProductId(pid);
 			spr.setSpecialId(specialId);
 			sprList.add(spr);
 		}
			specialProductRefService.insertSpecialProductRef(sprList);
			addMessage(redirectAttributes, "添加专题商品成功");
		}
 		return "redirect:"+Global.getAdminPath()+"/product/special/updateSelectProduct?specialId="+specialId+"&updateFlag="+updateFlag;
 	}
 	
 	@RequestMapping(value = "getbysort")
 	@ResponseBody
 	public ReturnMsg<Special> getbysort(String sort){
 		ReturnMsg<Special> msg = new ReturnMsg<Special>();
 		Integer sortCount= specialService.getbysort(sort);
 		if(sortCount>0){
 			msg.setStatus(false);
 		}else{
 			msg.setStatus(true);
 		}
 		return msg;
 	}
 	
 	@RequestMapping(value = "treeData")
 	@ResponseBody
	public List<Map<String, Object>> treeData(HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		try {
			List<Special> list = specialService.queryCommonSpecial();
			if(list!=null && list.size()!=0){
				for (int i = 0; i < list.size(); i++) {
					Special s = list.get(i);
					Map<String, Object> map = Maps.newHashMap();
					map.put("id", s.getId());
					map.put("name", s.getSpecialTitle());
					mapList.add(map);
			    }
				
			}
			
		} catch (Exception e) {
			logger.error("分类树结构获取专题失败", e);
		}
		return mapList;
	}
}