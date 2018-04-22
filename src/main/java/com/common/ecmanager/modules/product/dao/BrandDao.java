/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.common.ecmanager.modules.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.common.ecmanager.common.persistence.CrudDao;
import com.common.ecmanager.common.persistence.annotation.MyBatisDao;
import com.common.ecmanager.modules.product.entity.Brand;

/**
 * 商品品牌DAO接口
 * @author gaven
 * @version 2015-06-13
 */
@MyBatisDao
public interface BrandDao extends CrudDao<Brand> {
	/**
	 * 根据分类id删除品牌
	 * @param productCategoryId
	 */
	public void batchDeleteByProductCategoryId(@Param("productCategoryId") String productCategoryId);
	/**
	 * 根据品牌ID查询商品是否下架
	 * @param id
	 * @return
	 */
	public List<String> queryProductIsMarketableByBankId(Brand brand);
	/**
	 * 根据分类id查询品牌信息
	 * @param productCategoryId
	 */
	public List<Brand> queryBrandByProductCategoryId(@Param("productCategoryId") String productCategoryId);
}