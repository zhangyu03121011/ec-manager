/**
 * Copyright &copy; 2012-2014 <a href="http://www.easypay.com.hk/basicframework">basicframework</a> All rights reserved.
 */
package com.uib.ecmanager.modules.prize.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.service.CrudService;
import com.uib.ecmanager.modules.prize.dao.PrizeActivityDao;
import com.uib.ecmanager.modules.prize.entity.PrizeActivity;

/**
 * 抽奖活动Service
 * @author gyq
 * @version 2017-01-21
 */
@Service
@Transactional(readOnly = true)
public class PrizeActivityService extends CrudService<PrizeActivityDao, PrizeActivity> {

	public PrizeActivity get(String id) {
		return super.get(id);
	}
	
	public List<PrizeActivity> findList(PrizeActivity prizeActivity) {
		return super.findList(prizeActivity);
	}
	
	public Page<PrizeActivity> findPage(Page<PrizeActivity> page, PrizeActivity prizeActivity) {
		return super.findPage(page, prizeActivity);
	}
	
	@Transactional(readOnly = false)
	public void save(PrizeActivity prizeActivity) {
		super.save(prizeActivity);
	}
	
	@Transactional(readOnly = false)
	public void delete(PrizeActivity prizeActivity) {
		super.delete(prizeActivity);
	}
	
}