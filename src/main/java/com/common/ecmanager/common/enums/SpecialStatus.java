package com.common.ecmanager.common.enums;

/**
 * 商品专题状态
 * @author chengw
 *
 */
public enum SpecialStatus {
	
	uneffect("未生效","0"),
	
	effect("生效","1"),
	
	overdue("过期","2");
	
	private String description;
	
	private String value;
	
	private SpecialStatus(String description,String value){
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
