/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.common.ecmanager.modules.product.dao;

import com.common.ecmanager.common.persistence.CrudDao;
import com.common.ecmanager.common.persistence.annotation.MyBatisDao;
import com.common.ecmanager.modules.product.entity.ProductPriceRef;

/**
 * 商品会员价格关联表DAO接口
 * @author luogc
 * @version 2016-07-16
 */
@MyBatisDao
public interface ProductPriceRefDao extends CrudDao<ProductPriceRef> {
	/**
	 * 根据商品id删除关联价格
	 */
	public void deleteByProductId(String productId);

}