/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.common.ecmanager.modules.product.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.ecmanager.common.persistence.Page;
import com.common.ecmanager.common.service.CrudService;
import com.common.ecmanager.common.utils.StringUtils;
import com.common.ecmanager.modules.product.dao.ProductSpecificationDao;
import com.common.ecmanager.modules.product.dao.ProductSpecificationRefDao;
import com.common.ecmanager.modules.product.dao.SpecificationGroupDao;
import com.common.ecmanager.modules.product.entity.ProductSpecification;
import com.common.ecmanager.modules.product.entity.SpecificationGroup;
import com.common.util.UUIDGenerator;

/**
 * 商品规格Service
 * 
 * @author gaven
 * @version 2015-06-13
 */
@Service
@Transactional(readOnly = true)
public class SpecificationGroupService extends CrudService<SpecificationGroupDao, SpecificationGroup> {

	@Autowired
	private ProductSpecificationDao productSpecificationDao;
	@Autowired
	private ProductSpecificationRefDao productSpecificationRefDao;
	@Autowired
	private SpecificationGroupDao specificationGroupDao;

	public SpecificationGroup get(String id) {
		SpecificationGroup specificationGroup = super.get(id);
		specificationGroup.setProductSpecificationList(
				productSpecificationDao.findList(new ProductSpecification(specificationGroup)));
		return specificationGroup;
	}

	public List<SpecificationGroup> findList(SpecificationGroup specificationGroup) {
		return super.findList(specificationGroup);
	}

	public List<SpecificationGroup> findList(String productCategoryId, String goodsId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productCategoryId", productCategoryId);
		map.put("goodsId", goodsId);
		return specificationGroupDao.querySpecificationGroupByMap(map);
	}

	public Page<SpecificationGroup> findPage(Page<SpecificationGroup> page, SpecificationGroup specificationGroup) {
		return super.findPage(page, specificationGroup);
	}

	@Transactional(readOnly = false)
	public void save(SpecificationGroup specificationGroup) {
		super.save(specificationGroup);
				
		for (ProductSpecification productSpecification : specificationGroup.getProductSpecificationList()) {
//			if (productSpecification.getId() == null) {
//				continue; 
//			}
			if (ProductSpecification.DEL_FLAG_NORMAL.equals(productSpecification.getDelFlag())) {
				if (StringUtils.isBlank(productSpecification.getId())) {
					productSpecification.setMerchantNo(specificationGroup.getMerchantNo());
					productSpecification.setSpecificationGroup(specificationGroup);
					productSpecification.preInsert();
					productSpecificationDao.insert(productSpecification);
				}
			} else {
				productSpecificationDao.delete(productSpecification);
			}
		}
	}

	@Transactional(readOnly = false)
	public void update(SpecificationGroup specificationGroup) {
		super.update(specificationGroup);
		for (ProductSpecification productSpecification : specificationGroup.getProductSpecificationList()) {
			if (StringUtils.isBlank(productSpecification.getId())) {
				productSpecification.setId(UUIDGenerator.getUUID());
				productSpecification.setSpecificationGroup(specificationGroup);
				productSpecification.setMerchantNo(specificationGroup.getMerchantNo());
				productSpecification.preInsert();
				productSpecificationDao.insert(productSpecification);
				continue;
			}
			if (ProductSpecification.DEL_FLAG_NORMAL.equals(productSpecification.getDelFlag())) {

				productSpecification.preUpdate();
				productSpecificationDao.update(productSpecification);

			} else {
				productSpecificationDao.delete(productSpecification);
			}
		}
	}

	@Transactional(readOnly = false)
	public void delete(SpecificationGroup specificationGroup) {
		super.delete(specificationGroup);
		productSpecificationDao.delete(new ProductSpecification(specificationGroup));
	}

	/**
	 * 根据商品id查询规格组集合
	 * 
	 * @param productId
	 * @return
	 */
	public List<SpecificationGroup> querySpecificationGroupsByProductId(String productId) {
		// List<String> specificationIds = productSpecificationRefDao
		// .querySpecificationIdsByProductId(productId);
		// 下面查询需要优化
		List<ProductSpecification> specifications = productSpecificationDao.querySpecificationsByProductId(productId);
		List<SpecificationGroup> groups = specificationGroupDao.querySpecificationGroupsByProductId(productId);
		for (ProductSpecification specification : specifications) {
			String groupId = specification.getSpecificationGroup().getId();
			for (SpecificationGroup specificationGroup : groups) {
				if (specificationGroup.getId().equals(groupId)) {
					specificationGroup.getProductSpecificationList().add(specification);
					break;
				}
			}
		}
		return groups;
	}
	
	/*
	 * 根据参数组查商品ID
	 * 
	 */
	public List<String> queryProductIdBySpecificationGroup(SpecificationGroup specificationGroup){
		List<String> ids = new ArrayList<String>();
		if(StringUtils.isBlank(specificationGroup.getId())){
			return null;
		}else{
			ids = specificationGroupDao.queryProductIdBySpecificationGroup(specificationGroup);
		}
		return ids;
	}
	
	/**
	 * 根据商品ID查询商品是否上架
	 * @param id
	 * @return
	 */
	public String queryProductIsMarketable(String id){
		String result = "";
		if(StringUtils.isBlank(id)){
			return null;
		}else{
			result = specificationGroupDao.queryProductIsMarketable(id);
		}
		return result;
		
	}
	
}