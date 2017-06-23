/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.order.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.uib.ecmanager.common.persistence.CrudDao;
import com.uib.ecmanager.common.persistence.annotation.MyBatisDao;
import com.uib.ecmanager.modules.coupon.entity.CouponCode;
import com.uib.ecmanager.modules.order.entity.OrderTable;

/**
 * 订单DAO接口
 * @author limy
 * @version 2015-06-08
 */
@MyBatisDao
public interface OrderTableDao extends CrudDao<OrderTable> {
	public OrderTable findOrderTableByOrderNo(String orderNo);

	
	public void updateOrderTable(OrderTable orderTable);
	
	/**
	 * 根据购物车商品项编号查询出B端分享人当前等级的商品价格
	 * @param cartItemId
	 * @return
	 */
	public java.util.Map<String, Object> queryRecommendMeberByOrderNoAndProductId(@Param("orderNo") String orderNo,@Param("productId") String productId,@Param("reMemberIdString") String reMemberIdString);


	public List<OrderTable> findOrderNoByCouponCode(CouponCode c);
	
	/**
	 * 发货时，更新订单信息
	 * @param orderTable
	 * @throws Exception
	 */
	public void updateShippingInfo(OrderTable orderTable) throws Exception;
}