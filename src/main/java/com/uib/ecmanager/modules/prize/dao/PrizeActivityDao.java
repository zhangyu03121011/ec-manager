/**
 * Copyright &copy; 2012-2014 <a href="http://www.easypay.com.hk/basicframework">basicframework</a> All rights reserved.
 */
package com.uib.ecmanager.modules.prize.dao;


import com.uib.ecmanager.common.persistence.CrudDao;
import com.uib.ecmanager.common.persistence.annotation.MyBatisDao;
import com.uib.ecmanager.modules.prize.entity.PrizeActivity;

/**
 * 抽奖活动DAO接口
 * @author gyq
 * @version 2017-01-21
 */
@MyBatisDao
public interface PrizeActivityDao extends CrudDao<PrizeActivity> {
	
}