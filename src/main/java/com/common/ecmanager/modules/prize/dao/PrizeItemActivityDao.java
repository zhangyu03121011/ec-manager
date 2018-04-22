/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.common.ecmanager.modules.prize.dao;

import com.common.ecmanager.common.persistence.CrudDao;
import com.common.ecmanager.common.persistence.annotation.MyBatisDao;
import com.common.ecmanager.modules.prize.entity.PrizeItemActivity;

/**
 * 抽奖活动副表DAO接口
 * @author luoxf
 * @version 2017-01-22
 */
@MyBatisDao
public interface PrizeItemActivityDao extends CrudDao<PrizeItemActivity> {

	void batchUpdateNumber(String id);
	
}