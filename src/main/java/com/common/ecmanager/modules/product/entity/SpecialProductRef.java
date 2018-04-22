/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.common.ecmanager.modules.product.entity;

import com.common.ecmanager.common.persistence.DataEntity;

/**
 * 商品专题关联列表Entity
 * @author limy
 * @version 2016-07-14
 */
public class SpecialProductRef extends DataEntity<SpecialProductRef> {
	
	private static final long serialVersionUID = 1L;
	private String specialId;		// 专题编号
	private Product product;		// 商品编号
	private String productId;
	private String updateFlag;
	private int activityNum;  //活动数量
	private int limitNum;     //限购数量
	private double activityCost; //活动价格
	private int salesNum; //活动销量
	
	public SpecialProductRef() {
		super();
	}

	public SpecialProductRef(String id){
		super(id);
	}


	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getSpecialId() {
		return specialId;
	}

	public void setSpecialId(String specialId) {
		this.specialId = specialId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(String updateFlag) {
		this.updateFlag = updateFlag;
	}

	public int getActivityNum() {
		return activityNum;
	}

	public void setActivityNum(int activityNum) {
		this.activityNum = activityNum;
	}

	public int getLimitNum() {
		return limitNum;
	}

	public void setLimitNum(int limitNum) {
		this.limitNum = limitNum;
	}

	public double getActivityCost() {
		return activityCost;
	}

	public void setActivityCost(double activityCost) {
		this.activityCost = activityCost;
	}

	public int getSalesNum() {
		return salesNum;
	}

	public void setSalesNum(int salesNum) {
		this.salesNum = salesNum;
	}

	
}