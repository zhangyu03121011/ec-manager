/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.order.dao;
import java.util.List;

import com.uib.ecmanager.common.persistence.CrudDao;
import com.uib.ecmanager.common.persistence.annotation.MyBatisDao;
import com.uib.ecmanager.modules.order.entity.OrderTableItem;

/**
 * 订单DAO接口
 * @author limy
 * @version 2015-06-08
 */
@MyBatisDao
public interface OrderTableItemDao extends CrudDao<OrderTableItem> {
	//更新订单项的退货状态
	public void updateReturnStatus(OrderTableItem orderTableItem) throws Exception;
	
	public List<OrderTableItem>  findByTableId(OrderTableItem orderTableItem) throws Exception;
	
	/**
	 * 根据订单号查询订单项数量
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	public int  countOrderItemByOrderNo(String orderNo) throws Exception;
}