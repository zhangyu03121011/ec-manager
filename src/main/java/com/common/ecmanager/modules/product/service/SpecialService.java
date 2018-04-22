/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.common.ecmanager.modules.product.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.ecmanager.common.enums.SpecialStatus;
import com.common.ecmanager.common.persistence.Page;
import com.common.ecmanager.common.service.CrudService;
import com.common.ecmanager.modules.product.dao.SpecialDao;
import com.common.ecmanager.modules.product.dao.SpecialProductRefDao;
import com.common.ecmanager.modules.product.entity.Special;

/**
 * 专题信息Service
 * @author limy
 * @version 2016-07-14
 */
@Service
@Transactional(readOnly = true)
public class SpecialService extends CrudService<SpecialDao, Special> {
	
	private Logger logger = LoggerFactory.getLogger(SpecialService.class);

	@Autowired
	private SpecialProductRefDao specialProductRefDao;
	
	@Autowired
	private SpecialDao specialDao;
	
	public Special get(String id) {
		return super.get(id);
	}
	
	public List<Special> findList(Special special) {
		return super.findList(special);
	}
	
	public Page<Special> findPage(Page<Special> page, Special special) {
		return super.findPage(page, special);
	}
	
	@Transactional(readOnly = false)
	public void save(Special special) {
		super.save(special);
	}
	
	@Transactional(readOnly = false)
	public void update(Special special) {
		super.update(special);
	}
	
	@Transactional(readOnly = false)
	public void delete(Special special) {
		super.delete(special);
		specialProductRefDao.deleteRef(special.getId());
	}
	
	@Transactional(readOnly = false)
	public Integer getbysort(String sort){
		return specialDao.getbysort(sort);
	}
	
	@Transactional(readOnly = false)
	public void updateFlag(String id){
		specialDao.updateFlag(id);
	}
	
	@Transactional(readOnly = false)
	public void updateStatues() throws Exception{
		//设置为1的map
		Map<String,Object> status1Map = new HashMap<String,Object>();
		List<String> id1List = new ArrayList<String>();
		//设置为2的map
		Map<String,Object> status2Map = new HashMap<String,Object>();
		List<String> id2List = new ArrayList<String>();
		List<Special> checkData = specialDao.querySpecialForSetStatus();
		
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		Date nowDate = new Date();
		String nowDateStr = df.format(nowDate);
		int nowDateInt = Integer.parseInt(nowDateStr);
		for (Special special : checkData) {
			if(null != special.getBeginDate() && null != special.getEndDate()){
				String beginDateStr = df.format(special.getBeginDate());
				int beginDateInt = Integer.parseInt(beginDateStr);
				String endDateStr = df.format(special.getEndDate());
				int endDateInt = Integer.parseInt(endDateStr);
				//专题过期
				if(nowDateInt < beginDateInt || nowDateInt > endDateInt){
					if(!SpecialStatus.overdue.getValue().equals(special.getStatus())){
						id2List.add(special.getId());
					}
				}
				
				//专题生效
				if(nowDateInt >= beginDateInt && nowDateInt <= endDateInt){
					if(!SpecialStatus.effect.getValue().equals(special.getStatus())){
						id1List.add(special.getId());
					}
				}
			}
		}
		
		//设置生效
		if(id1List.size() > 0){
			status1Map.put("idList",id1List);
			status1Map.put("status", SpecialStatus.effect.getValue());
			
			try {
				specialDao.batchSetStatus(status1Map);
				logger.info("===["+ id1List.size() +"] records set status=1");
			} catch (Exception e) {
				logger.error("set status=1 error:" + e);
			}
		}else{
			logger.info("===no records set status=1");
		}
		
		//设置过期
		if(id2List.size() > 0){
			status2Map.put("idList",id2List);
			status2Map.put("status", SpecialStatus.overdue.getValue());
			try {
				specialDao.batchSetStatus(status2Map);
				logger.info("===["+ id2List.size() +"] records need to set status=2");
			} catch (Exception e) {
				logger.error("set status=2 error:" + e);
			}
		}else{
			logger.info("===no records set status=2");
		}
	}
	
	/**
	 * 获取非过期的普通专题
	 */
	@Transactional(readOnly = false)
	public List<Special> queryCommonSpecial() throws Exception{
		List<Special> list=new ArrayList<Special>();
		try {
			list=specialDao.queryCommonSpecial();
		} catch (Exception e) {
			logger.error("获取非过期的普通专题出错:" + e);
		}
		return list;
	}
	
	public Integer checkSort(Map<String,String> parm) throws Exception{
		return specialDao.checkSort(parm);
	}
}