/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.common.ecmanager.modules.supplier.dao;

import com.common.ecmanager.common.persistence.CrudDao;
import com.common.ecmanager.common.persistence.annotation.MyBatisDao;
import com.common.ecmanager.modules.supplier.entity.Supplier;

/**
 * 供应商管理DAO接口
 * @author luogc
 * @version 2016-08-22
 */
@MyBatisDao
public interface SupplierDao extends CrudDao<Supplier> {
	
}