package com.uib.ecmanager.modules.product.entity;

import com.uib.ecmanager.common.persistence.DataEntity;

/**
 * 专题和商品关系实体
 * @author chengw
 *
 */
public class SpecialProductRefBatch extends DataEntity<SpecialProductRefBatch>{

	private static final long serialVersionUID = 1L;
	private String specialId; 	 //专题id
	private String productId; 	 //产品id
	private String activtyPrice; //活动价
	private String activtyCount; //活动数量
	private String limitCount;   //限购数量
	private String activtyStock; //活动库存
	private String salesNum; 	 //活动累计销量
	private String isValid;   	 //活动是否过期 0-有效 1-无效
	private String specialType;	 //专题类型 0-专题 1-内购
	
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
	public String getActivtyPrice() {
		return activtyPrice;
	}
	public void setActivtyPrice(String activtyPrice) {
		this.activtyPrice = activtyPrice;
	}
	public String getActivtyCount() {
		return activtyCount;
	}
	public void setActivtyCount(String activtyCount) {
		this.activtyCount = activtyCount;
	}
	public String getLimitCount() {
		return limitCount;
	}
	public void setLimitCount(String limitCount) {
		this.limitCount = limitCount;
	}
	public String getActivtyStock() {
		return activtyStock;
	}
	public void setActivtyStock(String activtyStock) {
		this.activtyStock = activtyStock;
	}
	public String getSalesNum() {
		return salesNum;
	}
	public void setSalesNum(String salesNum) {
		this.salesNum = salesNum;
	}
	public String getIsValid() {
		return isValid;
	}
	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}
	public String getSpecialType() {
		return specialType;
	}
	public void setSpecialType(String specialType) {
		this.specialType = specialType;
	}
	
}
