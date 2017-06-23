package com.uib.ecmanager.common.enums;
/**
 * 用户类型枚举类
 * @author：George Rom
 * @date：2016年7月15日 上午10:09:05
 */
public enum UserType { 
	/** c1消费者 */
	c1_customer("c1消费者",1),
	/** c2消费者*/
	c2_customer("c2消费者",3),
	/** 分销商 */
	distributor("分销商",2);
	
	private String description;
	private int index;
	
	private UserType(String description,int index){
		this.description = description;
		this.index = index;
	}
	
    public static String getDescription(int index){
    	for (UserType t : UserType.values()) {
            if (t.getIndex() == index) {
                return t.description;
            }
        }
        return null;
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
