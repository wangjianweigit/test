package com.tk.sys.util;

/**
 * 
 * Copyright (c), 2018, Tongku
 * FileName : MqQueueKeyEnum.java
 * 
 *
 * @author yejingquan
 * @version 1.00
 * @date 2018年9月19日
 */
public enum MqQueueKeyEnum {
	
	/** 更新商品吊牌价 */
    ESB_PRODUCT_PRIZE_TAG_UPDATE("esb.product.prize.tag.update"),
    /** 财务记账消息队列 */
    ESB_FINANCE_PAYMENT_RECORD("esb.finance.payment.record"),
    /** 退款清分明细消息队列 */
    ESB_LIQUIDATION_ORDER_RETURN("esb.liquidation.order.return"),
    /** 待清分明细消息队列 */
    ESB_LIQUIDATION_ORDER_SETTLE("esb.liquidation.order.settle"),
    /** 自定义消息推送 */
    ESB_PUSH_MESSAGE_SEND("esb.push.message.send"),
    /** 自定义消息推送终止 */
    ESB_PUSH_MESSAGE_CANCEL("esb.push.message.cancel"),
    ;

	private String key;

    MqQueueKeyEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
