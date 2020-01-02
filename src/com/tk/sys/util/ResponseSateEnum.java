package com.tk.sys.util;

/**
 * Created by shif on 2017/5/2.
 */
public enum ResponseSateEnum {
    SUCCESS("1","表示成功"),
    FAIL("0","表示失败"),
    ;
    public String value;//实际值
    public String des;//状态描述

    ResponseSateEnum(String value, String des) {
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
