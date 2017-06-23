package com.uib.ecmanager.modules.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uib.ecmanager.modules.mem.service.PrizeRecommenderService;

/**
 * 更新中奖纪录状态表
 * @author Administrator
 *
 */
@Component
public class UpdatePrizeStatusTask {
	
	private Logger logger = LoggerFactory.getLogger(UpdatePrizeStatusTask.class);
	
	@Autowired
	private PrizeRecommenderService prizeRecommenderService;
	
	public void updatePrizeStatus(){
		logger.info("update prize status begin...");
		try {
			prizeRecommenderService.updatePrizeStatus();
		} catch (Exception e) {
			logger.error("update prize status error:" + e);
		}
		logger.info("update prize status end...");
	}
}
