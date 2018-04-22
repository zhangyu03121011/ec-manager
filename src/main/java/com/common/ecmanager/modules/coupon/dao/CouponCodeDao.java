/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.common.ecmanager.modules.coupon.dao;

import java.util.List;

import com.common.ecmanager.common.persistence.CrudDao;
import com.common.ecmanager.common.persistence.annotation.MyBatisDao;
import com.common.ecmanager.modules.coupon.entity.Coupon;
import com.common.ecmanager.modules.coupon.entity.CouponCode;

/**
 * 优惠券DAO接口
 * @author limy
 * @version 2015-06-15
 */
@MyBatisDao
public interface CouponCodeDao extends CrudDao<CouponCode> {
	/**
	 * 判断优惠码是否存在
	 * 
	 * @param code
	 *            号码(忽略大小写)
	 * @return 优惠码是否存在
	 */
	boolean codeExists(String code);

	/**
	 * 根据号码查找优惠码
	 * 
	 * @param code
	 *            号码(忽略大小写)
	 * @return 优惠码，若不存在则返回null
	 */
	CouponCode findByCode(String code);

	/**
	 * 生成优惠码
	 * 
	 * @param coupon
	 *            优惠券
	 * @param member
	 *            会员
	 * @return 优惠码
	 */
	CouponCode build(Coupon coupon);

	/**
	 * 生成优惠码
	 * 
	 * @param coupon
	 *            优惠券
	 * @param member
	 *            会员
	 * @param count
	 *            数量
	 * @return 优惠码
	 */
	List<CouponCode> builder(Coupon coupon);


	/**
	 * 查找优惠码数量
	 * 
	 * @param coupon
	 *            优惠券
	 * @return 优惠码数量
	 */
	Integer totalCount(Coupon coupon);
	/**
	 * 查找已用的优惠码数量
	 * 
	 * @param coupon
	 *            优惠券
	 * @return 优惠码数量
	 */
	Integer usedCount(Coupon coupon);
	/**
	 * 根据优惠劵id查询所有的优惠码
	 */
	public List<CouponCode> findAllList(CouponCode couponCode);
	
	
}