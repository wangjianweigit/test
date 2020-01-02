package com.tk.oms.decoration.control;
/**
 * 装修页面类型枚举类
 * Copyright (c), 2017, Tongku
 * FileName : DecoratePageTypeEnum.java
 * 
 *
 * @author yejingquan
 * @version 1.00
 * @date 2017-6-28
 */
public enum DecoratePageTypeEnum {
	
	HOME_PAGE("2","首页"),
	NORMAL_PAGE("1","普通装修页"),
	PRODUCT_LIST_PAGE("3","商品列表页"),
    ;
	public String value;//实际值
    public String des;//状态描述

    DecoratePageTypeEnum(String value, String des) {
        this.value = value;
        this.des = des;
    }

    public String getValue() {
        return value;
    }

    public String getDes() {
        return des;
    }
}
