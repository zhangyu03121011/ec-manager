/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.coupon.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.common.util.StringUtil;
import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.web.BaseController;
import com.uib.ecmanager.modules.coupon.dao.CouponCodeDao;
import com.uib.ecmanager.modules.coupon.entity.Coupon;
import com.uib.ecmanager.modules.coupon.entity.CouponCode;
import com.uib.ecmanager.modules.coupon.service.CouponCodeService;
import com.uib.ecmanager.modules.coupon.service.CouponService;
import com.uib.ecmanager.modules.mem.entity.MemMember;
import com.uib.ecmanager.modules.order.entity.OrderTable;
import com.uib.ecmanager.modules.order.service.OrderTableService;
import com.uib.ecmanager.modules.sys.service.SystemService;

/**
 * 优惠码Controller
 * @author limy
 * @version 2016-06-2
 */
@Controller
@RequestMapping(value = "${adminPath}/coupon/couponCode")
public class CouponCodeController extends BaseController {

	@Autowired
	private CouponService couponService;
	@Autowired
	private CouponCodeService couponCodeService;
	@Autowired
	private CouponCodeDao couponCodeDao;
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private OrderTableService orderTableService;
	
	/**
	 * @Title: couponCodelist 
	 * @Description: 根据优惠券编码查询所有的优惠码
	 * @param @param coupon
	 * @param @param request
	 * @param @param response
	 * @param @param model
	 * @param @return
	 * @return String
	 * @throws
	 */
	@RequiresPermissions("coupon:coupon:view")
	@RequestMapping(value = {"couponCodelist", ""})
	public String couponCodelist(Coupon coupon, HttpServletRequest request, HttpServletResponse response, Model model) {
		CouponCode couponCode = new CouponCode();
		couponCode.setCoupon(coupon);
		Page<CouponCode> page = couponCodeService.findPage(new Page<CouponCode>(request, response), couponCode); 
		List<CouponCode> couponCodelist = page.getList();
		for(CouponCode c:couponCodelist){
			List<OrderTable> orderTableList = orderTableService.findOrderNoByCouponCode(c);
			//处理订单号
			c.setOrderNoList(orderTableList);
			MemMember memMember = c.getMemMember();
			//处理微信昵称
			if(null != memMember && StringUtil.isNotEmpty(memMember.getUsername())) {
				try {
					memMember.setUsername(URLDecoder.decode(memMember.getUsername(), "utf-8"));
				} catch (UnsupportedEncodingException e) {
					logger.info("解析微信昵称异常");
				}
			}
		}
		model.addAttribute("page", page);
		return "modules/coupon/couponCodeList";
	}
}