/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.common.ecmanager.modules.product.dao;

import org.apache.ibatis.annotations.Param;

import com.common.ecmanager.common.persistence.TreeDao;
import com.common.ecmanager.common.persistence.annotation.MyBatisDao;
import com.common.ecmanager.modules.product.entity.ProductCategory;

/**
 * 商品分类DAO接口
 * 
 * @author kevin
 * @version 2015-06-12
 */
@MyBatisDao
public interface ProductCategoryDao extends TreeDao<ProductCategory> {
	/**
	 * 判断是否末级分类
	 * 
	 * @return
	 */
	public Boolean isLastStage(@Param("productCategoryId") String productCategoryId);
}