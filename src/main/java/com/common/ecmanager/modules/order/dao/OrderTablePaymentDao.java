/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.common.ecmanager.modules.order.dao;

import com.common.ecmanager.common.persistence.CrudDao;
import com.common.ecmanager.common.persistence.annotation.MyBatisDao;
import com.common.ecmanager.modules.order.entity.OrderTablePayment;

/**
 * 订单DAO接口
 * @author limy
 * @version 2015-06-08
 */
@MyBatisDao
public interface OrderTablePaymentDao extends CrudDao<OrderTablePayment> {
	
}