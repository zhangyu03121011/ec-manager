/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.common.ecmanager.modules.ad.dao;

import com.common.ecmanager.common.persistence.CrudDao;
import com.common.ecmanager.common.persistence.annotation.MyBatisDao;
import com.common.ecmanager.modules.ad.entity.Advertisement;

/**
 * 广告管理DAO接口
 * @author gaven
 * @version 2015-06-06
 */
@MyBatisDao
public interface AdvertisementDao extends CrudDao<Advertisement> {
	
}