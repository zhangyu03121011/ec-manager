/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.coupon.dao;

import com.uib.ecmanager.common.persistence.CrudDao;
import com.uib.ecmanager.common.persistence.annotation.MyBatisDao;
import com.uib.ecmanager.modules.coupon.entity.Coupon;

/**
 * 优惠券DAO接口
 * @author limy
 * @version 2015-06-15
 */
@MyBatisDao
public interface CouponDao extends CrudDao<Coupon> {
	/**
     *判断指定指定专题或指定商品下的优惠劵是否有重复
	 */
	public Integer checkCoupon(Coupon coupon);
}