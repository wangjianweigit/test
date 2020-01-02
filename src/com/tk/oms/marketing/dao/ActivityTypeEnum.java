package com.tk.oms.marketing.dao;
/**
 * 活动类型枚举类型
 * Copyright (c), 2017, Tongku
 * FileName : ActivityTypeEnum.java
 *活动类型；
 * @author songwangwen
 * @version 1.00
 * @date 2018-12-13
 */
public enum ActivityTypeEnum {
	
	SALE_ACTIVITY("1","限时折扣"),
	PREORDER_ACTIVITY("2","订货会"),
	PRESELL_ACTIVITY("4","预售活动"),
	CLEAR_ACTIVITY("5","一口清活动"),
    ;
	public String value;//实际值
    public String des;//状态描述

    ActivityTypeEnum(String value, String des) {
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
