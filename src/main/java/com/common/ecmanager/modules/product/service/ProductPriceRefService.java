/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.common.ecmanager.modules.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.ecmanager.common.persistence.Page;
import com.common.ecmanager.common.service.CrudService;
import com.common.ecmanager.modules.product.dao.ProductPriceRefDao;
import com.common.ecmanager.modules.product.entity.ProductPriceRef;

/**
 * 商品会员价格关联表Service
 * @author luogc
 * @version 2016-07-16
 */
@Service
@Transactional(readOnly = true)
public class ProductPriceRefService extends CrudService<ProductPriceRefDao, ProductPriceRef> {
	
	public ProductPriceRef get(String id) {
		return super.get(id);
	}
	
	public List<ProductPriceRef> findList(ProductPriceRef productPriceRef) {
		return super.findList(productPriceRef);
	}
	
	public Page<ProductPriceRef> findPage(Page<ProductPriceRef> page, ProductPriceRef productPriceRef) {
		return super.findPage(page, productPriceRef);
	}
	
	@Transactional(readOnly = false)
	public void save(ProductPriceRef productPriceRef) {
		super.save(productPriceRef);
	}
	/**
	 * 批量插入
	 * @param productPriceRef
	 */
	@Transactional(readOnly = false)
	public void insertList(List<ProductPriceRef> productPriceRefList) {		
		for (ProductPriceRef productPriceRef : productPriceRefList) {
			if (productPriceRef.getIsNewRecord()){
				productPriceRef.preInsert();
				dao.insert(productPriceRef);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void update(ProductPriceRef productPriceRef) {
		super.update(productPriceRef);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProductPriceRef productPriceRef) {
		super.delete(productPriceRef);
	}
	
}