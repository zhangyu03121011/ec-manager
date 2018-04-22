/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.common.ecmanager.modules.mem.dao;

import java.util.List;

import com.common.ecmanager.common.persistence.CrudDao;
import com.common.ecmanager.common.persistence.annotation.MyBatisDao;
import com.common.ecmanager.modules.mem.entity.PrizeRecommender;

/**
 * 中奖记录DAO接口
 * @author heyh
 * @version 2017-01-21
 */
@MyBatisDao
public interface PrizeRecommenderDao extends CrudDao<PrizeRecommender> {

	List<PrizeRecommender> queryPrizeStatus();

	void batchUpdateStatus(String id);
	
}