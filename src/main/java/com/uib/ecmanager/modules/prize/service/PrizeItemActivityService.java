/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.prize.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.service.CrudService;
import com.uib.ecmanager.modules.prize.dao.PrizeItemActivityDao;
import com.uib.ecmanager.modules.prize.entity.PrizeItemActivity;

/**
 * 抽奖活动副表Service
 * @author luoxf
 * @version 2017-01-22
 */
@Service
@Transactional(readOnly = true)
public class PrizeItemActivityService extends CrudService<PrizeItemActivityDao, PrizeItemActivity> {
	@Autowired
	private PrizeItemActivityDao prizeItemActivityDao;
	
	public PrizeItemActivity get(String id) {
		return super.get(id);
	}
	
	public List<PrizeItemActivity> findList(PrizeItemActivity prizeItemActivity) {
		return super.findList(prizeItemActivity);
	}
	
	public Page<PrizeItemActivity> findPage(Page<PrizeItemActivity> page, PrizeItemActivity prizeItemActivity) {
		return super.findPage(page, prizeItemActivity);
	}
	
	@Transactional(readOnly = false)
	public void save(PrizeItemActivity prizeItemActivity) {
		super.save(prizeItemActivity);
	}
	
	@Transactional(readOnly = false)
	public void update(PrizeItemActivity prizeItemActivity) {
		super.update(prizeItemActivity);
	}
	
	@Transactional(readOnly = false)
	public void delete(PrizeItemActivity prizeItemActivity) {
		super.delete(prizeItemActivity);
	}
	@Transactional(readOnly = false)
	public void batchUpdateNumber(String id) {
		prizeItemActivityDao.batchUpdateNumber(id);
	}
	
}