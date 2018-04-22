/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.common.ecmanager.modules.delivery.dao;

import com.common.ecmanager.common.persistence.CrudDao;
import com.common.ecmanager.common.persistence.annotation.MyBatisDao;
import com.common.ecmanager.modules.delivery.entity.DeliveryCorporation;

/**
 * 物流公司DAO接口
 * @author gaven
 * @version 2015-06-08
 */
@MyBatisDao
public interface DeliveryCorporationDao extends CrudDao<DeliveryCorporation> {
	
}