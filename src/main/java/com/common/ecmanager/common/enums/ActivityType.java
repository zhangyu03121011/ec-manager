package com.common.ecmanager.common.enums;

/**
 * 商品活动类型
 * @author chengw
 *
 */
public enum ActivityType {
	
	special("专题","0"),
	purchase("内购","1");

	private String description;
	
	private String value;
	
	private ActivityType(String description,String value){
		this.description = description;
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
