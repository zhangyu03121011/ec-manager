package com.uib.ecmanager.modules.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uib.ecmanager.modules.product.service.SpecialService;

/**
 * 更新专题和内购是否有效(isvalid字段)
 * 
 * @author chengw
 *
 */
@Component
public class UpdatePurchaseStatusTask {
	
	private Logger logger = LoggerFactory.getLogger(UpdatePurchaseStatusTask.class);
	
	@Autowired
	private SpecialService specialService;
	
	public void updateStatus(){
		
		logger.info("update status begin...");
		try {
			specialService.updateStatues();
		} catch (Exception e) {
			logger.error("update purchase status error:" + e);
		}
		
		
		logger.info("update status end...");
		
	}

}
