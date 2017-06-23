/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.product.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.service.CrudService;
import com.uib.ecmanager.common.utils.StringUtils;
import com.uib.ecmanager.modules.product.dao.BrandDao;
import com.uib.ecmanager.modules.product.entity.Brand;

/**
 * 商品品牌Service
 * @author gaven
 * @version 2015-06-13
 */
@Service
@Transactional(readOnly = true)
public class BrandService extends CrudService<BrandDao, Brand> {
	@Autowired
	private BrandDao brandDao;

	public Brand get(String id) {
		return super.get(id);
	}
	
	public List<Brand> findList(Brand brand) {
		return super.findList(brand);
	}
	
	public Page<Brand> findPage(Page<Brand> page, Brand brand) {
		return super.findPage(page, brand);
	}
	
	@Transactional(readOnly = false)
	public void save(Brand brand) {
		super.save(brand);
	}
	
	@Transactional(readOnly = false)
	public void update(Brand brand) {
		super.update(brand);
	}
	
	@Transactional(readOnly = false)
	public void delete(Brand brand) {
		super.delete(brand);
	}
	
	
	public List<String> queryProductIsMarketableByBankId(Brand brand){
		List<String> resultList = new ArrayList<String>();
		//String result ="";
		if(StringUtils.isBlank(brand.getId())){
			return null;
		}else{
			resultList = brandDao.queryProductIsMarketableByBankId(brand);
		}
		return resultList;
		
	}
	/**
	 * 根据分类id查询品牌信息
	 * @param productCategoryId
	 */
	public List<Brand> queryBrandByProductCategoryId(@Param("productCategoryId") String productCategoryId){
		List<Brand> brand = new ArrayList<Brand>();
		if(StringUtils.isBlank(productCategoryId)){
			return null;
		}else{
			brand = brandDao.queryBrandByProductCategoryId(productCategoryId);
		}
		return brand;
		
	}
	
}