/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.common.ecmanager.modules.mem.dao;

import com.common.ecmanager.common.persistence.CrudDao;
import com.common.ecmanager.common.persistence.annotation.MyBatisDao;
import com.common.ecmanager.modules.mem.entity.WithdrawalApplyFor;

/**
 * 提现管理表DAO接口
 * @author luogc
 * @version 2016-07-28
 */
@MyBatisDao
public interface WithdrawalApplyForDao extends CrudDao<WithdrawalApplyFor> {

	void updateRemark(WithdrawalApplyFor withdrawalApplyFor);
	
}