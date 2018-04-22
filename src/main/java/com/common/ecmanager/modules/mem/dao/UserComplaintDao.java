/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.common.ecmanager.modules.mem.dao;

import com.common.ecmanager.common.persistence.CrudDao;
import com.common.ecmanager.common.persistence.annotation.MyBatisDao;
import com.common.ecmanager.modules.mem.entity.UserComplaint;

/**
 * 会员投诉DAO接口
 * @author luogc
 * @version 2016-01-12
 */
@MyBatisDao
public interface UserComplaintDao extends CrudDao<UserComplaint> {

}