/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.common.ecmanager.modules.order.dao;

import java.util.List;

import com.common.ecmanager.common.persistence.CrudDao;
import com.common.ecmanager.common.persistence.annotation.MyBatisDao;
import com.common.ecmanager.modules.order.entity.OrderReturnsRef;

/**
 * 退货单DAO接口
 * @author limy
 * @version 2015-06-08
 */
@MyBatisDao
public interface OrderReturnsRefDao extends CrudDao<OrderReturnsRef> {
	public List<OrderReturnsRef> findReturnsRefByOrderNo(String orderNo);
}