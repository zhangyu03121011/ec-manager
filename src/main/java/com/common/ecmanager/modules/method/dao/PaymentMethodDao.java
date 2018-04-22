/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.common.ecmanager.modules.method.dao;

import com.common.ecmanager.common.persistence.CrudDao;
import com.common.ecmanager.common.persistence.annotation.MyBatisDao;
import com.common.ecmanager.modules.method.entity.PaymentMethod;

/**
 * 支付方式DAO接口
 * @author limy
 * @version 2015-07-15
 */
@MyBatisDao
public interface PaymentMethodDao extends CrudDao<PaymentMethod> {
	
}