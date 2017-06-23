/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.prize.entity;

import java.util.Date;

import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.uib.ecmanager.common.persistence.DataEntity;

/**
 * 抽奖活动副表Entity
 * @author luoxf
 * @version 2017-01-22
 */
public class PrizeItemActivity extends DataEntity<PrizeItemActivity> {
	
	private static final long serialVersionUID = 1L;
	private String prizeId;		// 奖品主表Id
	private String awardNumber;		// 奖品个数
	private String awardId;		// 奖品
	private String isVirtual;		// 是否虚拟
	private String number;		// 数量
	private String prizeNumber;		// 已中奖个数
	private String name;
	private String probability; //概率
	private String angle;//角度
	private String prizeGrade;//奖品等级
	private Date startDate;
	private Date endDate;
	
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public PrizeItemActivity() {
		super();
	}

	public PrizeItemActivity(String id){
		super(id);
	}

	@Length(min=0, max=64, message="奖品主表Id长度必须介于 0 和 64 之间")
	public String getPrizeId() {
		return prizeId;
	}

	public void setPrizeId(String prizeId) {
		this.prizeId = prizeId;
	}
	
	@Digits(fraction = 0, integer = 10)
	@Length(min=0, max=20, message="奖品个数长度必须介于 0 和 20 之间")
	public String getAwardNumber() {
		return awardNumber;
	}

	public void setAwardNumber(String awardNumber) {
		this.awardNumber = awardNumber;
	}
	
	@Length(min=0, max=64, message="奖品长度必须介于 0 和 64 之间")
	public String getAwardId() {
		return awardId;
	}

	public void setAwardId(String awardId) {
		this.awardId = awardId;
	}
	
	@Length(min=0, max=1, message="是否虚拟长度必须介于 0 和 1 之间")
	public String getIsVirtual() {
		return isVirtual;
	}

	public void setIsVirtual(String isVirtual) {
		this.isVirtual = isVirtual;
	}
	
	@Length(min=0, max=20, message="数量长度必须介于 0 和 20 之间")
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	@Digits(fraction = 0, integer = 10)
	@Length(min=0, max=20, message="已中奖个数长度必须介于 0 和 20 之间")
	public String getPrizeNumber() {
		return prizeNumber;
	}

	public void setPrizeNumber(String prizeNumber) {
		this.prizeNumber = prizeNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Digits(fraction = 0, integer = 10)
	public String getProbability() {
		return probability;
	}

	public void setProbability(String probability) {
		this.probability = probability;
	}
	@Digits(fraction = 0, integer = 10)
	public String getAngle() {
		return angle;
	}

	public void setAngle(String angle) {
		this.angle = angle;
	}

	public String getPrizeGrade() {
		return prizeGrade;
	}

	public void setPrizeGrade(String prizeGrade) {
		this.prizeGrade = prizeGrade;
	}
	
	
	
}