/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.mem.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.uib.ecmanager.common.persistence.DataEntity;

/**
 * 推广记录Entity
 * @author heyh
 * @version 2017-01-03
 */
public class RecommenderLog extends DataEntity<RecommenderLog> {
	
	private static final long serialVersionUID = 1L;
	private String productId;		// 推广商品ID
	private RecommenderLog parent;	// 父级Id(推广人Id)
	private String parentIds;		// 所有父级(三级推广人Id)
	private String memberId;		// 被推广人ID
	private String memberStatusCode;		// 被推广人状态(1、点击     2、已关注)
	private String memberStatusName;		// 被推广人状态
	private Date createTime;		// create_time
	private Date updateTime;		// update_time
	private String parentId;        //推广人Id
	private String parentName;		//推广人
	private String memMemberName;	//被推广人
	private String productName;		//产品名称
	private String productCode;		//产品编号
	private int linkCount;
	private int clickCount;
	
	public int getLinkCount() {
		return linkCount;
	}

	public void setLinkCount(int linkCount) {
		this.linkCount = linkCount;
	}

	public int getClickCount() {
		return clickCount;
	}

	public void setClickCount(int clickCount) {
		this.clickCount = clickCount;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getMemMemberName() {
		return memMemberName;
	}

	public void setMemMemberName(String memMemberName) {
		this.memMemberName = memMemberName;
	}

	public RecommenderLog() {
		super();
	}

	public RecommenderLog(String id){
		super(id);
	}

	@Length(min=0, max=64, message="推广商品ID长度必须介于 0 和 64 之间")
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@JsonBackReference
	public RecommenderLog getParent() {
		return parent;
	}

	public void setParent(RecommenderLog parent) {
		this.parent = parent;
	}
	
	@Length(min=0, max=264, message="所有父级(三级推广人Id)长度必须介于 0 和 264 之间")
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	@Length(min=0, max=64, message="被推广人ID长度必须介于 0 和 64 之间")
	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	@Length(min=0, max=64, message="被推广人状态(1、点击     2、已关注)长度必须介于 0 和 64 之间")
	public String getMemberStatusCode() {
		return memberStatusCode;
	}

	public void setMemberStatusCode(String memberStatusCode) {
		this.memberStatusCode = memberStatusCode;
	}
	
	@Length(min=0, max=64, message="被推广人状态长度必须介于 0 和 64 之间")
	public String getMemberStatusName() {
		return memberStatusName;
	}

	public void setMemberStatusName(String memberStatusName) {
		this.memberStatusName = memberStatusName;
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

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	
}