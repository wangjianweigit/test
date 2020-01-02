package com.tk.oms.product.entity;

/**
 * 商品操作业务类型枚举类
 * @author zhenghui
 * @date  2019-05-15
 */
public enum OperationTypeEnum {
	/**
	 * 商品操作业务类型
	 */
	ADD_PRODUCT(1,"创建商品"),
	EDIT_PRODUCT(2,"编辑商品"),
	DELETE_PRODUCT(3,"删除商品"),
	OTHER_PRODUCT(4,"其他");
	/**
	 * 实际值
	 */
	public int value;
	/**
	 * 状态描述
	 */
    public String des;

    OperationTypeEnum(int value, String des) {
        this.value = value;
        this.des = des;
    }

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}
}
