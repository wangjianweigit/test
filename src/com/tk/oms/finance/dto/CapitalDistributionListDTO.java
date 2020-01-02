package com.tk.oms.finance.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Copyright (c), 2018, TongKu
 * FileName : CapitalDistributionListDTO
 * 资金清分列表
 * @author wangjianwei
 * @version 1.00
 * @date 2019/7/29 14:47
 */
public class CapitalDistributionListDTO {
    /** 清分状态：1、尚未清分 2、已清分 4、终止清分*/
    private int distribution_state;

    //    部分清分 3、全部清分 4、终止清

    /** 清分日期 */
    private Date liquidation_date;

    /** 金额 */
    private BigDecimal settlement_amount;

    /** 清分流水号 */
    private String settlement_number;

    public int getDistribution_state () {
        return distribution_state;
    }

    public void setDistribution_state (int distribution_state) {
        this.distribution_state = distribution_state;
    }

    public BigDecimal getSettlement_amount () {
        return settlement_amount;
    }

    public void setSettlement_amount (BigDecimal settlement_amount) {
        this.settlement_amount = settlement_amount;
    }

    public String getSettlement_number () {
        return settlement_number;
    }

    public void setSettlement_number (String settlement_number) {
        this.settlement_number = settlement_number;
    }

    @DateTimeFormat (pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat (pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getLiquidation_date () {
        return liquidation_date;
    }

    public void setLiquidation_date (Date liquidation_date) {
        this.liquidation_date = liquidation_date;
    }
}
