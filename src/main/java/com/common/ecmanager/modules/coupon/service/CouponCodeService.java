/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.common.ecmanager.modules.coupon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.ecmanager.common.service.CrudService;
import com.common.ecmanager.modules.coupon.dao.CouponCodeDao;
import com.common.ecmanager.modules.coupon.dao.CouponDao;
import com.common.ecmanager.modules.coupon.entity.Coupon;
import com.common.ecmanager.modules.coupon.entity.CouponCode;

/**
 * 优惠券Service
 * @author limy
 * @version 2015-06-15
 */
@Service
@Transactional(readOnly = true)
public class CouponCodeService extends CrudService<CouponCodeDao, CouponCode> {

	@Autowired
	private CouponCodeDao couponCodeDao;
	@Autowired
	private CouponDao couponDao;
	

	public List<CouponCode> findList(CouponCode couponCode) {
		return super.findList(couponCode);
	}
	/**
	 * 根据优惠劵id查询所有的优惠码
	 */
	public List<CouponCode> findAllList(CouponCode couponCode){
		return couponCodeDao.findAllList(couponCode);
	}
	/**
	 * 查找优惠码数量
	 * 
	 * @param coupon
	 *            优惠券
	 * @return 优惠码数量
	 */
	public Integer totalCount(Coupon coupon){
		return couponCodeDao.totalCount(coupon);
	}

}