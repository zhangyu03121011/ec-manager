/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.mem.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.uib.ecmanager.common.persistence.DataEntity;
import com.uib.ecmanager.modules.prize.entity.PrizeActivity;
import com.uib.ecmanager.modules.prize.entity.PrizeItemActivity;

/**
 * 中奖记录Entity
 * @author heyh
 * @version 2017-01-21
 */
public class PrizeRecommender extends DataEntity<PrizeRecommender> {
	
	private static final long serialVersionUID = 1L;
	private PrizeActivity prizeActivity;		// 奖品主表
	private PrizeItemActivity prizeItemActivity;		// 奖品副表
	private MemMember member;		// 会员id
	private String prizeStatus;		// 中奖状态(0:未领取 1:已领取 2：已过期)
	private Date startTime;		// 开始生效时间
	private Date endTime;		// 结束时间
	private Date createTime;		// 创建时间
	private Date updateTime;		// 更新时间
	private String memMemberName;	//会员名
	
	
	public String getMemMemberName() {
		return memMemberName;
	}

	public void setMemMemberName(String memMemberName) {
		this.memMemberName = memMemberName;
	}

	public PrizeRecommender() {
		super();
	}

	public PrizeRecommender(String id){
		super(id);
	}

	public PrizeActivity getPrizeActivity() {
		return prizeActivity;
	}

	public void setPrizeActivity(PrizeActivity prizeActivity) {
		this.prizeActivity = prizeActivity;
	}

	public PrizeItemActivity getPrizeItemActivity() {
		return prizeItemActivity;
	}

	public void setPrizeItemActivity(PrizeItemActivity prizeItemActivity) {
		this.prizeItemActivity = prizeItemActivity;
	}

	public MemMember getMember() {
		return member;
	}

	public void setMember(MemMember member) {
		this.member = member;
	}

	@Length(min=0, max=1, message="中奖状态(0:未领取 1:已领取 2：已过期)长度必须介于 0 和 1 之间")
	public String getPrizeStatus() {
		return prizeStatus;
	}

	public void setPrizeStatus(String prizeStatus) {
		this.prizeStatus = prizeStatus;
	}
	
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
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}