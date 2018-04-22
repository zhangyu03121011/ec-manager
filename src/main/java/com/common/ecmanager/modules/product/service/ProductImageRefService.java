/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.common.ecmanager.modules.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.ecmanager.common.persistence.Page;
import com.common.ecmanager.common.service.CrudService;
import com.common.ecmanager.modules.product.dao.ProductImageRefDao;
import com.common.ecmanager.modules.product.entity.ProductImageRef;

/**
 * 商品图片Service
 * @author gaven
 * @version 2015-05-27
 */
@Service
@Transactional(readOnly = true)
public class ProductImageRefService extends CrudService<ProductImageRefDao, ProductImageRef> {

	
	public ProductImageRef get(String id) {
		ProductImageRef productImageRef = super.get(id);
		return productImageRef;
	}
	
	public List<ProductImageRef> findList(ProductImageRef productImageRef) {
		return super.findList(productImageRef);
	}
	
	public Page<ProductImageRef> findPage(Page<ProductImageRef> page, ProductImageRef productImageRef) {
		return super.findPage(page, productImageRef);
	}
	
	@Transactional(readOnly = false)
	public void save(ProductImageRef productImageRef) {
		super.save(productImageRef);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProductImageRef productImageRef) {
		super.delete(productImageRef);
	}
	
}