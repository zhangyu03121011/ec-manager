/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.common.ecmanager.modules.mem.dao;

import com.common.ecmanager.common.persistence.CrudDao;
import com.common.ecmanager.common.persistence.annotation.MyBatisDao;
import com.common.ecmanager.modules.mem.entity.Deposit;

/**
 * 预存款DAO接口
 * @author limy
 * @version 2015-06-15
 */
@MyBatisDao
public interface DepositDao extends CrudDao<Deposit> {
	
}