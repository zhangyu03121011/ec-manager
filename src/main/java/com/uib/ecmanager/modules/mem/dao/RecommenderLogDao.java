/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.mem.dao;

import java.util.List;

import com.uib.ecmanager.common.persistence.CrudDao;
import com.uib.ecmanager.common.persistence.annotation.MyBatisDao;
import com.uib.ecmanager.modules.mem.entity.RecommenderLog;

/**
 * 推广记录DAO接口
 * @author heyh
 * @version 2017-01-03
 */
@MyBatisDao
public interface RecommenderLogDao extends CrudDao<RecommenderLog> {

	List<RecommenderLog> findIdsByParentId(String parentId);

	RecommenderLog getByMemProId(RecommenderLog r);
	
}