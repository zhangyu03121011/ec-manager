/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.common.ecmanager.modules.coupon.entity;

import java.beans.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.common.ecmanager.common.persistence.DataEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;


/**
 * 优惠券Entity
 * @author limy
 * @version 2015-06-15
 */
public class Coupon extends DataEntity<Coupon> {
	
	private static final long serialVersionUID = 1L;
	private Date beginDate;		// 使用起始日期
	private Date endDate;		// 使用结束日期
	private String introduction;		// 介绍
	private boolean isExchange;		// 是否允许积分兑换
	private BigDecimal maximumPrice;		// 最大商品价格
	private Integer maximumQuantity;		// 最大商品数量
	private BigDecimal minimumPrice;		// 最小商品价格
	private Integer minimumQuantity;		// 最小商品数量
	private String name;		// 名称
	private Long point;		// 积分兑换数
	private String prefix;		// 前缀
	private int sum; 		//数量
	private int presentSum;  //赠送数量
	private String presentType; 	//赠送类型 1.购买满减
	private String priceExpression;		// 价格运算表达式
	private BigDecimal facePrice;	//面值
	private BigDecimal needConsumeBalance; //所需消费余额
	private String couponSource;	//优惠券来源
	private String couponRange;     //指定范围:1.指定专题 2.指定商品 3.全场通用
	private String specialId;       //专题id
	private String productId;       //商品id
	private List<CouponCode> couponCodeList = Lists.newArrayList();		// 子表列表
	
	/**用于页面展示，不参与持久化**/
	private String specialName;    //专题名字
	private String productName;    //商品名字
	private String isUsed;         //优惠码的状态 0未使用,1已使用,2已锁定,3已领取
	private Integer totalSum;      //总数量
	private Integer noUserSum;     //未使用数量
	
	public Coupon() {
		super();
	}

	public Coupon(String id){
		super(id);
	}
	
	
	public Integer getTotalSum() {
		return totalSum;
	}

	public void setTotalSum(Integer totalSum) {
		this.totalSum = totalSum;
	}

	public Integer getNoUserSum() {
		return noUserSum;
	}

	public void setNoUserSum(Integer noUserSum) {
		this.noUserSum = noUserSum;
	}

	public String getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSpecialName() {
		return specialName;
	}

	public void setSpecialName(String specialName) {
		this.specialName = specialName;
	}

	public String getCouponRange() {
		return couponRange;
	}

	public void setCouponRange(String couponRange) {
		this.couponRange = couponRange;
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

	public void setExchange(boolean isExchange) {
		this.isExchange = isExchange;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	@Transient
	public boolean getIsExchange() {
		return isExchange;
	}

	public void setIsExchange(boolean isExchange) {
		this.isExchange = isExchange;
	}
	
	
	@Length(min=0, max=32, message="名称长度必须介于 0 和 32 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Transient
	public Long getPoint() {
		return point;
	}

	public void setPoint(Long point) {
		this.point = point;
	}
	
	@Length(min=0, max=255, message="前缀长度必须介于 0 和 255 之间")
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	@Length(min=0, max=255, message="价格运算表达式长度必须介于 0 和 255 之间")
	public String getPriceExpression() {
		return priceExpression;
	}

	public void setPriceExpression(String priceExpression) {
		this.priceExpression = priceExpression;
	}
	
	public List<CouponCode> getCouponCodeList() {
		return couponCodeList;
	}

	public void setCouponCodeList(List<CouponCode> couponCodeList) {
		this.couponCodeList = couponCodeList;
	}
	@Transient
	public BigDecimal getMaximumPrice() {
		return maximumPrice;
	}
	public void setMaximumPrice(BigDecimal maximumPrice) {
		this.maximumPrice = maximumPrice;
	}
	@Transient
	public Integer getMaximumQuantity() {
		return maximumQuantity;
	}

	public void setMaximumQuantity(Integer maximumQuantity) {
		this.maximumQuantity = maximumQuantity;
	}
	@Transient
	public BigDecimal getMinimumPrice() {
		return minimumPrice;
	}

	public void setMinimumPrice(BigDecimal minimumPrice) {
		this.minimumPrice = minimumPrice;
	}
	@Transient
	public Integer getMinimumQuantity() {
		return minimumQuantity;
	}

	public void setMinimumQuantity(Integer minimumQuantity) {
		this.minimumQuantity = minimumQuantity;
	}
	
	/**
	 * 判断是否已开始
	 * 
	 * @return 是否已开始
	 */
	@Transient
	public boolean hasBegun() {
		return getBeginDate() == null || new Date().after(getBeginDate());
	}

	/**
	 * 判断是否已过期
	 * 
	 * @return 是否已过期
	 */
	@Transient
	public boolean hasExpired() {
		return getEndDate() != null && new Date().after(getEndDate());
	}

	public BigDecimal getFacePrice() {
		return facePrice;
	}

	public void setFacePrice(BigDecimal facePrice) {
		this.facePrice = facePrice;
	}

	public BigDecimal getNeedConsumeBalance() {
		return needConsumeBalance;
	}

	public void setNeedConsumeBalance(BigDecimal needConsumeBalance) {
		this.needConsumeBalance = needConsumeBalance;
	}

	public String getCouponSource() {
		return couponSource;
	}

	public void setCouponSource(String couponSource) {
		this.couponSource = couponSource;
	}
	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public String getPresentType() {
		return presentType;
	}

	public void setPresentType(String presentType) {
		this.presentType = presentType;
	}

	public int getPresentSum() {
		return presentSum;
	}

	public void setPresentSum(int presentSum) {
		this.presentSum = presentSum;
	}

	/**
	 * 计算优惠价格
	 * 
	 * @param quantity
	 *            商品数量
	 * @param price
	 *            商品价格
	 * @return 优惠价格
	 */
//	@Transient
//	public BigDecimal calculatePrice(Integer quantity, BigDecimal price) {
//		if (price == null || StringUtils.isEmpty(getPriceExpression())) {
//			return price;
//		}
//		BigDecimal result = new BigDecimal(0);
//		try {
//			Map<String, Object> model = new HashMap<String, Object>();
//			model.put("quantity", quantity);
//			model.put("price", price);
//			result = new BigDecimal(FreeMarkers.process("#{(" + getPriceExpression() + ");M50}", model));
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (TemplateException e) {
//			e.printStackTrace();
//		}
//		if (result.compareTo(price) > 0) {
//			return price;
//		}
//		return result.compareTo(new BigDecimal(0)) > 0 ? result : new BigDecimal(0);
//	}
}