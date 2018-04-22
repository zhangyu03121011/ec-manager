/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.common.ecmanager.modules.product.dao;

import java.util.List;

import com.common.ecmanager.common.persistence.CrudDao;
import com.common.ecmanager.common.persistence.annotation.MyBatisDao;
import com.common.ecmanager.modules.product.entity.ProductAreaRef;

/**
 * 商品区域关联表DAO接口
 * @author luogc
 * @version 2016-07-16
 */
@MyBatisDao
public interface ProductAreaRefDao extends CrudDao<ProductAreaRef> {
	public List<ProductAreaRef> findProductAreaRefList(String productId);
	public void deleteByProductId(String productId);
	public int batchDeleteArea(String productId[]);
	public void insertProductAreaRef(List<ProductAreaRef> parList);
}