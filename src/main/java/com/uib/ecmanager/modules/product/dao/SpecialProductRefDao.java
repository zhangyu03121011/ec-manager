/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.product.dao;

import java.util.List;

import com.uib.ecmanager.common.persistence.CrudDao;
import com.uib.ecmanager.common.persistence.annotation.MyBatisDao;
import com.uib.ecmanager.modules.product.entity.SpecialProductRef;
import com.uib.ecmanager.modules.product.entity.SpecialProductRefBatch;

/**
 * 商品专题关联列表DAO接口
 * @author limy
 * @version 2016-07-14
 */
@MyBatisDao
public interface SpecialProductRefDao extends CrudDao<SpecialProductRef> {
	public void insertSpecialProductRef(List<SpecialProductRef> sprList);
	public int deleteRef(String specialId);
	public int batchDelete(String productId[]);
	public void batchInsertSpecialProductRef(List<SpecialProductRefBatch> productList) throws Exception;
	public List<SpecialProductRefBatch> checkProductIsAddBySpecial(List<SpecialProductRefBatch> productList) throws Exception;
}