/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.product.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.uib.ecmanager.common.config.Global;
import com.uib.ecmanager.common.enums.ActivityType;
import com.uib.ecmanager.common.mapper.JsonMapper;
import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.utils.ReturnMsg;
import com.uib.ecmanager.common.utils.StringUtils;
import com.uib.ecmanager.common.web.BaseController;
import com.uib.ecmanager.modules.product.entity.Product;
import com.uib.ecmanager.modules.product.entity.Special;
import com.uib.ecmanager.modules.product.entity.SpecialProductRef;
import com.uib.ecmanager.modules.product.entity.SpecialProductRefBatch;
import com.uib.ecmanager.modules.product.service.ProductService;
import com.uib.ecmanager.modules.product.service.SpecialProductRefService;
import com.uib.ecmanager.modules.product.service.SpecialService;

/**
 * 内购信息Controller
 * @author cj
 * @version 2016-12-19
 */
@Controller
@RequestMapping(value = "${adminPath}/product/inAppPurchase")
public class InAppPurchaseController extends BaseController {

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
//		special.setType("1");
		special.setType(ActivityType.purchase.getValue());
		Page<Special> page = specialService.findPage(new Page<Special>(request, response), special); 
		model.addAttribute("page", page);
		return "modules/inApp/inAppList";
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
		return "modules/inApp/inAppForm";
	}
	
	@RequiresPermissions("product:special:view")
	@RequestMapping(value = "specialView")
	public String specialView(Special special, HttpServletRequest request,Model model) {
		Product product = new Product();
		product.setSpecialId(special.getId());
		product.setUpdateFlag(special.getUpdateFlag());
		model.addAttribute("product", product);
		model.addAttribute("special", special);
		return "modules/inApp/inAppView";
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
		return "modules/inApp/inAppupdateForm";
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
//		special.setType("1");
		special.setType(ActivityType.purchase.getValue());
		specialService.save(special);
		addMessage(redirectAttributes, "添加内购信息成功");
		return "redirect:"+Global.getAdminPath()+"/product/inAppPurchase/productList?specialId="+special.getId()+"&updateFlag="+special.getUpdateFlag();
	}
	
	@RequiresPermissions("product:special:view")
	@RequestMapping(value = "formView")
	public String formView(Special special, HttpServletRequest request,Model model) {
		Product product = new Product();
		product.setSpecialId(special.getId());
		product.setUpdateFlag(special.getUpdateFlag());
		model.addAttribute("product", product);
		model.addAttribute("special", special);
		return "modules/inApp/inAppFormView";
	}
	
	@RequiresPermissions("product:special:edit")
	@RequestMapping(value = "saveForm")
	public String saveFormView(Special special, Model model,RedirectAttributes redirectAttributes) {
		special.setUpdateFlag("2");
		specialService.update(special);
		addMessage(redirectAttributes, "修改内购信息成功");
		return "redirect:"+Global.getAdminPath()+"/product/inAppPurchase/productList?specialId="+special.getId()+"&updateFlag="+special.getUpdateFlag();
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
		addMessage(redirectAttributes, "修改内购信息成功");
		return "redirect:"+Global.getAdminPath()+"/product/inAppPurchase";
//		return "redirect:"+Global.getAdminPath()+"/product/inAppPurchase/updateProductList?specialId="+special.getId()+"&updateFlag="+special.getUpdateFlag();
	}
	
	@RequiresPermissions("product:special:edit")
	@RequestMapping(value = "delete")
	public String delete(Special special, RedirectAttributes redirectAttributes) {
		Date nowTime = new Date();
		if(nowTime.after(special.getBeginDate())&& nowTime.before(special.getEndDate())){
			addMessage(redirectAttributes, "有效期内的内购不能删除！");
		}else{
			specialService.delete(special);
			addMessage(redirectAttributes, "删除内购信息成功");
		}
		
		return "redirect:"+Global.getAdminPath()+"/product/inAppPurchase/?repage";
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
		model.addAttribute("product", product);
		model.addAttribute("searchProductRef", specialProductRef);
		}
		return "modules/inApp/inAppProductList";
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
		return "modules/inApp/updateInAppProductList";
	}
	
	@RequiresPermissions("product:special:edit")
	@RequestMapping(value = "deleteSpecialProduct")
	public String deleteSpecialProduct(SpecialProductRef specialProductRef, RedirectAttributes redirectAttributes,Model model) {
		specialProductRefService.delete(specialProductRef);
		addMessage(redirectAttributes, "删除内购商品成功");
		return "redirect:"+Global.getAdminPath()+"/product/inAppPurchase/selectProduct?specialId="+specialProductRef.getSpecialId();
	}
	
	@RequiresPermissions("product:special:edit")
	@RequestMapping(value = "updateDeleteSpecialProduct")
	public String updateDeleteSpecialProduct(SpecialProductRef specialProductRef, RedirectAttributes redirectAttributes,Model model) {
		specialProductRefService.delete(specialProductRef);
		addMessage(redirectAttributes, "删除内购商品成功");
		return "redirect:"+Global.getAdminPath()+"/product/inAppPurchase/updateSelectProduct?specialId="+specialProductRef.getSpecialId();
	}

	@RequiresPermissions("product:special:view")
	@RequestMapping(value = "productList")
	public String productList(Product product,HttpServletRequest request, HttpServletResponse response, Model model) {
		//Page<Product> page = productService.findPages(new Page<Product>(request, response), product);
//		Page<Product> page = productService.findProducts(new Page<Product>(request, response), product);
		Page<Product> page = null;
		try {
			page = productService.queryProductsExcludeBySpecialId(new Page<Product>(request, response), product);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("page", page);
		model.addAttribute("product", product);
		return "modules/inApp/inAppProductAddList";
	}
	
	@RequiresPermissions("product:special:view")
	@RequestMapping(value = "updateProductList")
	public String updateProductList(Product product,HttpServletRequest request, HttpServletResponse response, Model model) {
		//Page<Product> page = productService.findPages(new Page<Product>(request, response), product);
//		Page<Product> page = productService.findProducts(new Page<Product>(request, response), product);
		Page<Product> page = null;
		try {
			page = productService.queryProductsExcludeBySpecialId(new Page<Product>(request, response), product);
		} catch (Exception e) {
			logger.error("查询活动可选商品列表失败：" + e);
		}
		
		model.addAttribute("page", page);
		model.addAttribute("product", product);
		return "modules/inApp/inAppSpecialProductAddList";
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
			addMessage(redirectAttributes, "添加内购商品成功");
		}
 		return "redirect:"+Global.getAdminPath()+"/product/inAppPurchase/selectProduct?specialId="+specialId+"&updateFlag="+updateFlag;
 	}
	
	/**
	 * 设置(选择)内购商品
	 * @param productId
	 * @param specialId
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("product:special:view")
 	@RequestMapping(value="editProduct")
 	public String editProduct(String[] productId,String specialId,String updateFlag,HttpServletRequest request,HttpServletResponse response, Model model){
		List<Map<String,Object>> productList = null;
 		try {
			productList = productService.queryProductsByIds(productId);
		} catch (Exception e) {
			logger.info("editProduct->queryProductsByIds error:");
		}
 		Map<String,Object> map = new HashMap<String, Object>();
 		map.put("list", productList);
 		logger.info("grid json data" + JsonMapper.toJsonString(map));
 		
 		model.addAttribute("specialId", specialId);
 		model.addAttribute("updateFlag", updateFlag);
		model.addAttribute("productList", JsonMapper.toJsonString(map));
		return "modules/inApp/inAppProductEditList";
	}
	
	/**
	 * 批量编辑内购商品
	 * @param jsonObject
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("product:special:edit")
 	@RequestMapping(value="batchEditProduct",method = {RequestMethod.POST})
	@ResponseBody
	public ReturnMsg<String> batchEditProduct(@RequestBody JSONObject jsonObject,HttpServletRequest request,HttpServletResponse response){
		logger.info("batchEditProduct input parm jsonObject=" + jsonObject.toJSONString());
		ReturnMsg<String> msg = new ReturnMsg<String>();
		msg.setStatus(true);
		String jsonStr = jsonObject.getString("productList");
		List<SpecialProductRefBatch> productList = JSON.parseArray(jsonStr, SpecialProductRefBatch.class);
		List<SpecialProductRefBatch> checkList = null;
		try {
			checkList = specialProductRefService.checkProductIsAddBySpecial(productList);
			if(null!= checkList && checkList.size() > 0){
				msg.setStatus(false);
				msg.setMsg("选择的商品已经在其他专题，不能重复添加");
				return msg;
			}
		} catch (Exception ep) {
			logger.error("batch update checkProductIsAddBySpecial error:" + ep);
			msg.setStatus(false);
			msg.setMsg("程序逻辑异常");
			return msg;
		}
		
		try {
			specialProductRefService.batchInsertSpecialProductRef(productList);
		} catch (Exception e) {
			logger.error("batch update sepcial-product-ref error:" + e);
			msg.setStatus(false);
			msg.setMsg("程序逻辑异常");
		}
		return msg;
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
			addMessage(redirectAttributes, "添加内购商品成功");
		}
 		return "redirect:"+Global.getAdminPath()+"/product/inAppPurchase/updateSelectProduct?specialId="+specialId+"&updateFlag="+updateFlag;
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
 	
 	/**
 	 * 检验活动排序是否存在，如果是修改活动序号应当排除指定的专题id
 	 * @param sortNum
 	 * @param specialId
 	 * @return
 	 */
 	@RequestMapping(value = "checkSort")
 	@ResponseBody
 	public ReturnMsg<String> checkSort(@RequestParam String sortNum,@RequestParam(required=false) String specialId){
 		ReturnMsg<String> msg = new ReturnMsg<String>();
 		Map<String,String> parm = new HashMap<String, String>();
 		logger.info("check activity sort number input parm:sortNum=" + sortNum + ",specialId=" + specialId);
 		parm.put("sortNum", sortNum);
 		parm.put("specialId", specialId);
 		int sortCount = 0;
		try {
			sortCount = specialService.checkSort(parm);
		} catch (Exception e) {
			logger.error("check activity sort number error:" + e);
		}
 		if(sortCount>0){
 			msg.setStatus(false);
 			msg.setMsg("排序值【"+ sortNum +"】已经使用");
 		}else{
 			msg.setStatus(true);
 			msg.setMsg("排序值【"+ sortNum +"】可用");
 		}
 		return msg;
 	}
 	
}