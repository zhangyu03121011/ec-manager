/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.common.ecmanager.modules.product.dao;

import java.util.List;

import com.common.ecmanager.common.persistence.CrudDao;
import com.common.ecmanager.common.persistence.annotation.MyBatisDao;
import com.common.ecmanager.modules.product.entity.ProductComment;

/**
 * 商品评论DAO接口
 * @author gaven
 * @version 2015-10-22
 */
@MyBatisDao
public interface ProductCommentDao extends CrudDao<ProductComment> {
	List<ProductComment> selectByProductId(String productId);
}