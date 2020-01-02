package com.tk.oms.saas;

import java.io.Serializable;

/**
 * Copyright (c), 2016, Tongku
 * FileName : SaasResponse
 * 聚水潭请求结果
 *
 * @author wanghai
 * @version 1.00
 * @date 2017-11-23
 */
public class SaasResponse implements Serializable{

    /** 结果 */
    private boolean success = false;
    /** 结果信息 */
    private String message = "请求聚水潭接口失败";
    /** 是否有下一页 */
    private boolean has_next = false;
    /** 当前页 */
    private int page_index;
    /** 当前也数量 */
    private int page_size;
    /** 结果内容 */
    private Object datas;
    /** 店铺id **/
    private long shop_id;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isHas_next() {
        return has_next;
    }

    public void setHas_next(boolean has_next) {
        this.has_next = has_next;
    }

    public int getPage_index() {
        return page_index;
    }

    public void setPage_index(int page_index) {
        this.page_index = page_index;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    public Object getDatas() {
        return datas;
    }

    public void setDatas(Object datas) {
        this.datas = datas;
    }

    public long getShop_id() {
        return shop_id;
    }

    public void setShop_id(long shop_id) {
        this.shop_id = shop_id;
    }
}
