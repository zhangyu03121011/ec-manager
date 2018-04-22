/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.common.ecmanager.modules.order.dao;

import java.util.Map;

import com.common.ecmanager.common.persistence.CrudDao;
import com.common.ecmanager.common.persistence.annotation.MyBatisDao;
import com.common.ecmanager.modules.order.entity.OrderTableReturns;
import com.common.ecmanager.modules.order.entity.OrderTableReturnsItem;

/**
 * 退货单DAO接口
 * @author limy
 * @version 2015-06-08
 */
@MyBatisDao
public interface OrderTableReturnsDao extends CrudDao<OrderTableReturns> {

	/**
	 * @param orderTableReturns
	 */
	void updateStatus(OrderTableReturns orderTableReturns);
	
	OrderTableReturns queryOrderReturnBypId(Map<String,Object> map);
	
	/**
	 * 根据商品Id更新推荐表的结算状态
	 */
	public void updateRecommendProductLog(Map<String,Object> map) throws Exception;
	/**
	 * 根据商品编号、商品类型、推荐人id（可空）
	 */
	public OrderTableReturnsItem selectOrderItemQuantity(OrderTableReturns orderTableReturns) throws Exception;
	
	
	/**
	 * 根据订单号查询退货项数量
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	public int  countOrderReturnByOrderNo(String orderNo) throws Exception;
	
	/**
	 * 据订单号查询退货项表退货成功数量
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	public int  countOrderReturnSuccessByOrderNo(String orderNo) throws Exception;
}