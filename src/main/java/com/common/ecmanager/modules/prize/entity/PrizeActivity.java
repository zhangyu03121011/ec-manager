/**
 * Copyright &copy; 2012-2014 <a href="http://www.easypay.com.hk/basicframework">basicframework</a> All rights reserved.
 */
package com.common.ecmanager.modules.prize.entity;

import java.util.Date;

import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.Length;

import com.common.ecmanager.common.persistence.DataEntity;
import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 抽奖活动Entity
 * @author gyq
 * @version 2017-01-21
 */
public class PrizeActivity extends DataEntity<PrizeActivity> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 抽奖活动名称
	private String point;		// 积分数
	private String limits;		// 限制次数
	private Date startTime;
	private Date endTime;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public PrizeActivity() {
		super();
	}

	public PrizeActivity(String id){
		super(id);
	}

	@Length(min=0, max=200, message="抽奖活动名称长度必须介于 0 和 200 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Digits(fraction = 0, integer = 5)
	@Length(min=0, max=20, message="积分数长度必须介于 0 和 20 之间")
	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}
	@Digits(fraction = 0, integer = 5)
	public String getLimits() {
		return limits;
	}

	public void setLimits(String limits) {
		this.limits = limits;
	}
	
	
	
}