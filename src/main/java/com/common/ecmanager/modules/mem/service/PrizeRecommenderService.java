/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.common.ecmanager.modules.mem.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.ecmanager.common.persistence.Page;
import com.common.ecmanager.common.service.CrudService;
import com.common.ecmanager.modules.mem.dao.PrizeRecommenderDao;
import com.common.ecmanager.modules.mem.entity.PrizeRecommender;
import com.common.ecmanager.modules.prize.service.PrizeItemActivityService;

/**
 * 中奖记录Service
 * @author heyh
 * @version 2017-01-21
 */
@Service
@Transactional(readOnly = true)
public class PrizeRecommenderService extends CrudService<PrizeRecommenderDao, PrizeRecommender> {
	@Autowired
	private PrizeRecommenderDao prizeRecommenderDao;
	
	@Autowired
	private PrizeItemActivityService prizeItemActivityService;
	
	public PrizeRecommender get(String id) {
		return super.get(id);
	}
	
	public List<PrizeRecommender> findList(PrizeRecommender prizeRecommender) {
		return super.findList(prizeRecommender);
	}
	
	public Page<PrizeRecommender> findPage(Page<PrizeRecommender> page, PrizeRecommender prizeRecommender) {
		return super.findPage(page, prizeRecommender);
	}
	
	@Transactional(readOnly = false)
	public void save(PrizeRecommender prizeRecommender) {
		super.save(prizeRecommender);
	}
	
	@Transactional(readOnly = false)
	public void update(PrizeRecommender prizeRecommender) {
		super.update(prizeRecommender);
	}
	
	@Transactional(readOnly = false)
	public void delete(PrizeRecommender prizeRecommender) {
		super.delete(prizeRecommender);
	}
	
	@Transactional(readOnly = false)
	public void updatePrizeStatus() {
		Map<String,Object> map = new HashMap<String,Object>();
		List<String> list = new ArrayList<String>();
		List<String> prizeItemList = new ArrayList<String>();
		List<PrizeRecommender> prizeList = prizeRecommenderDao.queryPrizeStatus();
		Date newDate =  new Date();
		long newTime = newDate.getTime();
		//查找过期id
		for(PrizeRecommender prizeRecommender:prizeList){
			if(prizeRecommender.getStartTime()!= null && prizeRecommender.getEndTime() != null){
				long startTime = prizeRecommender.getStartTime().getTime();
				long endTime = prizeRecommender.getEndTime().getTime();
				if(newTime>endTime && startTime <= endTime){
					list.add(prizeRecommender.getId());
					prizeItemList.add(prizeRecommender.getPrizeItemActivity().getId());
					//设置失效状态
					prizeRecommenderDao.batchUpdateStatus(prizeRecommender.getId());
					//奖品加一
					prizeItemActivityService.batchUpdateNumber(prizeRecommender.getPrizeItemActivity().getId());
				}
			}
		}
	}
	
}