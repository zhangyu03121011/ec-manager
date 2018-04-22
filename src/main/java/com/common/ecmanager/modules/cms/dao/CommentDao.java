/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.common.ecmanager.modules.cms.dao;

import com.common.ecmanager.common.persistence.CrudDao;
import com.common.ecmanager.common.persistence.annotation.MyBatisDao;
import com.common.ecmanager.modules.cms.entity.Comment;

/**
 * 评论DAO接口
 * @author ThinkGem
 * @version 2013-8-23
 */
@MyBatisDao
public interface CommentDao extends CrudDao<Comment> {

}
