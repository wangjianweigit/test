package com.tk.oms.finance.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Copyright (c), 2018, TongKu
 * FileName : CapitalDistributionDetailDTO
 * 资金清分明细DTO
 * @author wangjianwei
 * @version 1.00
 * @date 2019/7/29 14:43
 */
public class CapitalDistributionDetailDTO {
    /** 备注 */
    private String remark;

    /** 最近清分时间 */
    private Date last_date;

    /** 最近交易流水号 */
    private String settlement_number;

    /** 结算金额 */
    private BigDecimal settlement_amount;

    private String order_number;

    private long stationed_user_id;

    private int settlement_state;

    /** 清分列表 */
    private List<CapitalDistributionListDTO> list;

    private List<CapitalDistributionListDTO> detailList;

    public String getRemark () {
        return remark;
    }

    public void setRemark (String remark) {
        this.remark = remark;
    }

    @DateTimeFormat (pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat (pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getLast_date () {
        return last_date;
    }

    public void setLast_date (Date last_date) {
        this.last_date = last_date;
    }

    public String getSettlement_number () {
        return settlement_number;
    }

    public void setSettlement_number (String settlement_number) {
        this.settlement_number = settlement_number;
    }

    public List<CapitalDistributionListDTO> getList () {
        return list;
    }

    public void setList (List<CapitalDistributionListDTO> list) {
        this.list = list;
    }

    public BigDecimal getSettlement_amount () {
        return settlement_amount;
    }

    public void setSettlement_amount (BigDecimal settlement_amount) {
        this.settlement_amount = settlement_amount;
    }

    public String getOrder_number () {
        return order_number;
    }

    public void setOrder_number (String order_number) {
        this.order_number = order_number;
    }

    public long getStationed_user_id () {
        return stationed_user_id;
    }

    public void setStationed_user_id (long stationed_user_id) {
        this.stationed_user_id = stationed_user_id;
    }

    public List<CapitalDistributionListDTO> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<CapitalDistributionListDTO> detailList) {
        this.detailList = detailList;
    }

    public int getSettlement_state() {
        return settlement_state;
    }

    public void setSettlement_state(int settlement_state) {
        this.settlement_state = settlement_state;
    }
}
