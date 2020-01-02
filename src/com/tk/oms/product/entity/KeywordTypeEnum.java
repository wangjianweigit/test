package com.tk.oms.product.entity;

/**
 * 商品操作日志类型枚举类
 * @author zhenghui
 * @date  2019-05-15
 */
public enum KeywordTypeEnum {

	/**
	 * 商品操作日志类型
	 */
	SAVE_DRAFT(1, "保存草稿"),
	PUBLISH_PRODUCT(2, "发布商品"),
	APPROVE_PRODUCT(3, "审核商品"),
	EDIT_PRODUCT(4, "编辑商品"),
	DELETE_PRODUCT(5, "删除商品"),
	PRODUCT_SHELVES(6, "商品上下架"),
	SKU_SHELVES(7, "SKU上下架"),
	PRODUCT_DISABLE(8, "商品启停用"),
	SKU_DISABLE(9, "SKU启停用"),
	COLOR_SORT(10, "颜色排序"),
	SHOP_CLASSIFY(11, "店铺分类"),
	MATERIAL_FIGURE(12, "商品材料图"),
	OUT_STOCK(13, "缺货订购"),
	SET_WRAPPER(14, "设置包材信息"),
	JOIN_ACTIVITY(15, "参加活动"),
	ACTIVITY_APPROVE(16, "活动商品审批"),
	EXIT_ACTIVITY(17, "退出活动"),
	SITE_DELAY(18, "站点延后时间"),
	MAINLY_POPULARIZE(19, "电商主推款"),
	SALE_SORT(20, "销量排序"),
	STOP_OUTSTOCK(21, "终止补货"),
	IS_PRIVATE(22, "商品共享");


	/**
	 * 实际值
	 */
	public int value;
	/**
	 * 状态描述
	 */
    public String des;

    KeywordTypeEnum(int value, String des) {
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
