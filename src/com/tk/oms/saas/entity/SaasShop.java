package com.tk.oms.saas.entity;

import java.util.Date;

/**
 * Copyright (c), 2016, Tongku
 * FileName : SaasShop
 * 聚水潭店铺信息
 * TBL_USER_SHOP
 *
 * @author wanghai
 * @version 1.00
 * @date 2017-12-07
 */
public class SaasShop {

    /** 编号 */
    private long id;
    /** 店铺ID */
    private long shop_id;
    /** 店铺类型：1、托管；2、对接 */
    private char type;
    /** 同步模式：1、同步所有；2、同步关联订单 */
    private char sync_type;
    /** 授权状态：1、未授权；2、已授权 */
    private char state;
    /** 所属会员 */
    private long user_id;
    /** 所属聚水潭公司 */
    private long company_id;
    /** 创建人ID */
    private long creater_id;
    /** 创建时间 */
    private Date create_date;
    /** 更新时间 */
    private Date update_date;
    /** 店铺名称 */
    private String shop_name;
    /** 库存同步启用状态：1、未启用；2、启用 */
    private char stock_sync_state;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getShop_id() {
        return shop_id;
    }

    public void setShop_id(long shop_id) {
        this.shop_id = shop_id;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public char getSync_type() {
        return sync_type;
    }

    public void setSync_type(char sync_type) {
        this.sync_type = sync_type;
    }

    public char getState() {
        return state;
    }

    public void setState(char state) {
        this.state = state;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getCompany_id() {
        return company_id;
    }

    public void setCompany_id(long company_id) {
        this.company_id = company_id;
    }

    public long getCreater_id() {
        return creater_id;
    }

    public void setCreater_id(long creater_id) {
        this.creater_id = creater_id;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public Date getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(Date update_date) {
        this.update_date = update_date;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public char getStock_sync_state() {
        return stock_sync_state;
    }

    public void setStock_sync_state(char stock_sync_state) {
        this.stock_sync_state = stock_sync_state;
    }
}
