package com.tk.oms.platform.entity;
/**
 * 限时折扣活动的活动场景id与名称的对应枚举
 * @author Reid
 * @date  2018-11-30  下午5:33:48
 */
public enum SceneTypeEnum {
	//场景类型 1.限时秒杀 2.爆款秒批 3.清仓大促
    MS(1,"限时秒杀"),
    MP(2,"爆款秒批"),
    QC(3,"清仓大促"),
    PPT(4,"品牌团")
    ;
    public int value;//实际值
    public String des;//状态描述

    SceneTypeEnum(int value, String des) {
        this.value = value;
        this.des = des;
    }

    public static String getDes(int key) {
        SceneTypeEnum[] fixedModuleEnums = values();
        for (SceneTypeEnum fixedModuleEnum : fixedModuleEnums) {
            if (fixedModuleEnum.getValue() == key) {
                return fixedModuleEnum.getDes();
            }
        }
        return null;
    }
    public int getValue() {
        return value;
    }

    public String getDes() {
        return des;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
