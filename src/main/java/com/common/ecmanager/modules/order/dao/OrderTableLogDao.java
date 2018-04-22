/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.common.ecmanager.modules.order.dao;

import com.common.ecmanager.common.persistence.CrudDao;
import com.common.ecmanager.common.persistence.annotation.MyBatisDao;
import com.common.ecmanager.modules.order.entity.OrderTableLog;

/**
 * 订单日志DAO接口
 * @author limy
 * @version 2015-06-01
 */
@MyBatisDao
public interface OrderTableLogDao extends CrudDao<OrderTableLog> {
	
}