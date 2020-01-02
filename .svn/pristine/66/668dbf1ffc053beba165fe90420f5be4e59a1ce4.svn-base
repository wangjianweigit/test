package com.tk.oms.platform.entity;

public enum FixedModuleEnum {

    FIXED_MODULE_BANNER("banner", "banner图"),
    FIXED_MODULE_SMALL_BANNER("small_banner", "banner小广告位"),
    FIXED_MODULE_UPDATE("update", "每日上新"),
    FIXED_MODULE_SECKILL("seckill", "限时秒杀"),
    FIXED_MODULE_BRAND("brand", "品牌街"),
    FIXED_MODULE_HOT("hot", "热卖TOP"),
    FIXED_MODULE_BUY("buy", "特惠抢购"),
    FIXED_MODULE_CLASSIFY("classify", "商品分类"),
    FIXED_MODULE_PRESELL("presell", "预售抢先"),
    FIXED_MODULE_CUSTOM("custom", "实力定制"),
    FIXED_MODULE_LIKE("like", "猜你喜欢"),
    FIXED_MODULE_MENU("menu", "快捷菜单"),
    FIXED_MODULE_NEW_SHOES("new_shoes", "最新童鞋"),
    FIXED_MODULE_ACTIVITY("activity", "活动模块"),
    FIXED_MODULE_PAST_PRESELL("past_presell", "往期预售"),
    FIXED_MODULE_GROUP("group", "商品分组"),
    FIXED_MODULE_CUSTOM_HOT("custom_hot", "自定义热区"),
    FIXED_MODULE_GIFT("gift", "礼品配件"),
    FIXED_MODULE_ADVERTISING("advertising", "广告位"),
    FIXED_MODULE_MAIN_CUSTOM("main_custom", "主推定制"),
    FIXED_MODULE_RANKING_CUSTOM("ranking_custom", "定制排行"),
    FIXED_MODULE_HOT_CUSTOM("hot_custom", "热门定制"),
    FIXED_MODULE_HOME_LIKE("home_like", "首页猜你喜欢"),
    FIXED_MODULE_HOME_CUSTOM("home_custom", "首页自定义区"),
    ;

    FixedModuleEnum(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public static String getName(String key) {
        FixedModuleEnum[] fixedModuleEnums = values();
        for (FixedModuleEnum fixedModuleEnum : fixedModuleEnums) {
            if (fixedModuleEnum.getKey().equals(key)) {
                return fixedModuleEnum.getName();
            }
        }
        return null;
    }

    private String key;
    private String name;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
