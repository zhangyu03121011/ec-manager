/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.uib.ecmanager.common.persistence.CrudDao;
import com.uib.ecmanager.common.persistence.annotation.MyBatisDao;
import com.uib.ecmanager.modules.product.entity.ParameterGroup;

/**
 * 商品参数组DAO接口
 * 
 * @author gaven
 * @version 2015-05-28
 */
@MyBatisDao
public interface ParameterGroupDao extends CrudDao<ParameterGroup> {
	/**
	 * 根据分类删除参数
	 * 
	 * @param productCategoryId
	 */
	public void batchDeleteByProductCategoryId(@Param("productCategoryId") String productCategoryId);
	/*
	 * 根据参数组查商品ID
	 * 
	 */
	public List<String> queryProductIdByParameterGroup(ParameterGroup parameterGroup);
	/**
	 * 根据商品ID查询商品是否上架
	 * @param id
	 * @return
	 */
	public String queryProductIsMarketable(String id);
}