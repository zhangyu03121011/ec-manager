/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.coupon.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.uib.ecmanager.common.config.Global;
import com.uib.ecmanager.common.enums.PresentType;
import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.utils.ReturnMsg;
import com.uib.ecmanager.common.utils.StringUtils;
import com.uib.ecmanager.common.web.BaseController;
import com.uib.ecmanager.modules.coupon.dao.CouponCodeDao;
import com.uib.ecmanager.modules.coupon.entity.Coupon;
import com.uib.ecmanager.modules.coupon.entity.CouponCode;
import com.uib.ecmanager.modules.coupon.service.CouponCodeService;
import com.uib.ecmanager.modules.coupon.service.CouponService;
import com.uib.ecmanager.modules.product.entity.Product;
import com.uib.ecmanager.modules.product.entity.Special;
import com.uib.ecmanager.modules.product.service.ProductService;
import com.uib.ecmanager.modules.product.service.SpecialService;
import com.uib.ecmanager.modules.sys.service.SystemService;
import com.uib.pbyt.service.ICreateCouponService;

/**
 * 优惠券Controller
 * @author limy
 * @version 2015-06-15
 */
@Controller
@RequestMapping(value = "${adminPath}/coupon/coupon")
public class CouponController extends BaseController {

	@Autowired
	private CouponService couponService;
	@Autowired
	private CouponCodeService couponCodeService;
	@Autowired
	private CouponCodeDao couponCodeDao;
	@Autowired
	private SystemService systemService;
	@Autowired
	private SpecialService specialService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ICreateCouponService createCouponService;
	
	@ModelAttribute
	public Coupon get(@RequestParam(required=false) String id) {
		Coupon entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = couponService.get(id);
			if(entity.getSpecialId()!=null && (!entity.getSpecialId().equals(""))){
				Special special=specialService.get(entity.getSpecialId());
				entity.setSpecialName(special.getSpecialTitle());
			}
			if(entity.getProductId()!=null && (!entity.getProductId().equals(""))){
				Product p=productService.get(entity.getProductId());
				entity.setProductName(p.getName());
			}
		}
		if (entity == null){
			entity = new Coupon();
		}
		return entity;
	}
	
	@RequiresPermissions("coupon:coupon:view")
	@RequestMapping(value = {"list", ""})
	public String list(Coupon coupon, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Coupon> page = couponService.findPage(new Page<Coupon>(request, response), coupon);
		//列表页设置 优惠劵总数  未使用数
		List<Coupon> list=page.getList();
		for(Coupon c:list){
			Integer totalSum=couponCodeService.totalCount(c);
//			Integer noUserSum=couponCodeService.totalCount(c);
			c.setIsUsed("0");
			c.setTotalSum(totalSum);
			c.setNoUserSum(totalSum);
			c.setIsUsed(null);
		}
		model.addAttribute("page", page);
		return "modules/coupon/couponList";
	}
	
	@RequiresPermissions("coupon:coupon:view")
	@RequestMapping(value = "form")
	public String form(Coupon coupon, Model model) {
		model.addAttribute("coupon", coupon);
		if(StringUtils.isNoneBlank(coupon.getId())){
			Integer totalSum=couponCodeService.totalCount(coupon);
			coupon.setIsUsed("0");
			Integer noUserSum=couponCodeService.totalCount(coupon);
			coupon.setTotalSum(totalSum);   
			coupon.setNoUserSum(noUserSum);
			coupon.setIsUsed(null);
		}
		//赠送类型
		/*Map<Integer,String> listPresentType = new HashMap<Integer,String>();
		for(PresentType pt : PresentType.values()){
			listPresentType.put(pt.getIndex(), pt.getDescription());
		}
		model.addAttribute("listPresentType", listPresentType);*/
		
		return "modules/coupon/couponForm";
	}
	
	@RequiresPermissions("coupon:coupon:view")
	@RequestMapping(value = "updateFormView")
	public String updateFormView(Coupon coupon, Model model) {
		model.addAttribute("coupon", coupon);
		//赠送类型
		Map<Integer,String> listPresentType = new HashMap<Integer,String>();
		for(PresentType pt : PresentType.values()){
			listPresentType.put(pt.getIndex(), pt.getDescription());
		}
		model.addAttribute("listPresentType", listPresentType);
		return "modules/coupon/couponupdateForm";
	}
	@RequiresPermissions("coupon:coupon:view")
	@RequestMapping(value = "couponBuild")
	public String couponBuild(String id, Model model) {
		Coupon coupon = couponService.get(id);
		model.addAttribute("coupon", coupon);
		model.addAttribute("totalCount", couponService.totalCount(coupon));
		model.addAttribute("usedCount", couponService.usedCount(coupon));
		return "modules/coupon/couponBuild";
	}

	@RequiresPermissions("coupon:coupon:save")
	@RequestMapping(value = "save")
	public String save(Coupon coupon, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, coupon)){
			return form(coupon, model);
		}
		couponService.save(coupon);
		addMessage(redirectAttributes, "保存优惠券成功");
		return "redirect:"+Global.getAdminPath()+"/coupon/coupon/?repage";
	}
	
	@RequiresPermissions("coupon:coupon:edit")
	@RequestMapping(value = "update")
	public String update(Coupon coupon, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, coupon)){
			return form(coupon, model);
		}
		if(coupon.getCouponRange().equals("1")){
			//专题
			coupon.setProductId("");
		}else if(coupon.getCouponRange().equals("2")){
			//商品
			coupon.setSpecialId("");
		}
		couponService.update(coupon);
		addMessage(redirectAttributes, "修改优惠券成功");
		return "redirect:"+Global.getAdminPath()+"/coupon/coupon/?repage";
	}
	
	@RequiresPermissions("coupon:coupon:edit")
	@RequestMapping(value = "delete")
	public String delete(Coupon coupon, RedirectAttributes redirectAttributes) {
		//判断在有效期内的优惠劵不能删除
		if(StringUtils.isNoneBlank(coupon.getId())){
			long star_date=coupon.getBeginDate().getTime();
			long end_date=coupon.getEndDate().getTime();
			long now_date=new Date().getTime();
			if((star_date<=now_date) && (end_date>=now_date)){
				addMessage(redirectAttributes, "优惠劵未过期，删除优惠券失败");
				return "redirect:"+Global.getAdminPath()+"/coupon/coupon/?repage";
			}
		}
		couponService.delete(coupon);
		addMessage(redirectAttributes, "删除优惠券成功");
		return "redirect:"+Global.getAdminPath()+"/coupon/coupon/?repage";
	}
	/**
	 * 下载优惠码
	 */
	/**@RequiresPermissions("coupon:coupon:edit")
	 * @throws Exception 
	@RequestMapping(value = "download")
	public ModelAndView download(String id ) {
		Coupon coupon = couponService.get(id);
		List<CouponCode> data = couponService.builder(coupon);
		String filename = coupon.getName() +"-"+ new SimpleDateFormat("yyyyMM").format(new Date()) + ".xls";
		String[] contents = new String[4];
		contents[0] = "优惠券: " + coupon.getName();
		contents[1] = "生成数量: " + coupon.getSum();
		contents[2] = "操作员: " + systemService.getCurrentUsername();
		contents[3] = "生成日期: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		return new ModelAndView(new ExcelView(filename, null, new String[] { "code" }, new String[] {"优惠券"}, null, null, data, contents));
	}**/
    
	@RequiresPermissions("coupon:coupon:edit")
	@RequestMapping(value = "download")
	public String download(String id,RedirectAttributes redirectAttributes){
		logger.info("生成优惠劵码入参couponId="+id);
		
		//判断是首次生成优惠码还是再次生成
		//true 是新增;false 是修改;
		boolean flag=true;
		
		//设置默认消息
		addMessage(redirectAttributes, "生成优惠码成功");
		
		try {
			CouponCode couponCode=new CouponCode();
			Coupon param_coupon=new Coupon();
			param_coupon.setId(id);
			couponCode.setCoupon(param_coupon);
			List<CouponCode> list=couponCodeService.findAllList(couponCode);
			if(list.size()!=0){
				flag=false;
			}
			
			Coupon coupon = couponService.get(id);
			//生成优惠码
			couponService.builder(coupon);
		
		}catch (Exception e) {
			logger.error("生成优惠码出错:{}",e);
			addMessage(redirectAttributes, "生成优惠码失败，程序逻辑异常");
		}
		
		//调用ec-pbyt-front接口把生成的优惠券放入spring容器，以便前端用户领取优惠券
		try {
			createCouponService.addCoupon(id,flag);
		} catch (Exception e) {
			logger.error("把优惠码放入ec-pbyt-front容器异常:{}",e);
			addMessage(redirectAttributes, "生成优惠码失败，调用ec-pbyt-front dubbo接口addCoupon出错");
		}
		
		return "redirect:"+Global.getAdminPath()+"/coupon/coupon/?repage";
	}
	/***
	 * 判断同种优惠劵类型是否有重复
	 */
	@ResponseBody
	@RequestMapping(value = "checkCoupon")
	public ReturnMsg<Object> checkCoupon(Coupon coupon, HttpServletRequest request, HttpServletResponse response) {
		ReturnMsg<Object> result=new ReturnMsg<Object>();
		try {
		    Integer count=couponService.checkCoupon(coupon);
		    if(count==0){
		    	result.setStatus(true);
		    }else{
		    	result.setStatus(false);
		    }
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(false);
		}
		return result;
	}
}