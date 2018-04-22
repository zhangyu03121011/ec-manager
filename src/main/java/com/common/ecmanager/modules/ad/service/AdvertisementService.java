/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.common.ecmanager.modules.ad.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.ecmanager.common.persistence.Page;
import com.common.ecmanager.common.service.CrudService;
import com.common.ecmanager.modules.ad.dao.AdvertisementDao;
import com.common.ecmanager.modules.ad.entity.Advertisement;

/**
 * 广告管理Service
 * @author gaven
 * @version 2015-06-06
 */
@Service
@Transactional(readOnly = true)
public class AdvertisementService extends CrudService<AdvertisementDao, Advertisement> {

	public Advertisement get(String id) {
		return super.get(id);
	}
	
	public List<Advertisement> findList(Advertisement advertisement) {
		return super.findList(advertisement);
	}
	
	public Page<Advertisement> findPage(Page<Advertisement> page, Advertisement advertisement) {
		return super.findPage(page, advertisement);
	}
	
	@Transactional(readOnly = false)
	public void save(Advertisement advertisement) {
		super.save(advertisement);
	}
	
	@Transactional(readOnly = false)
	public void update(Advertisement advertisement) {
		super.update(advertisement);
	}
	
	@Transactional(readOnly = false)
	public void delete(Advertisement advertisement) {
		super.delete(advertisement);
	}
	
}