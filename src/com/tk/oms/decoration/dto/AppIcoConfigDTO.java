package com.tk.oms.decoration.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tk.oms.sys.entity.RequestPublicParam;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Copyright (c); 2018; TongKu
 * FileName : AppIcoConfigDTO
 * 
 * @author wangjianwei
 * @version 1.00
 * @date 2019/7/12 10:13
 */
public class AppIcoConfigDTO extends RequestPublicParam {
    private long id;
    private String template_name;
    private int enable_state;
    private Date effect_start_time;
    private Date effect_end_time;
    private String head_general_url;
    private String head_activity_url;
    private String head_mall_url;
    private String head_movie_url;
    private String head_replacement_order_url;
    private String bottom_index_url;
    private String bottom_index_check_url;
    private String bottom_msg_url;
    private String bottom_msg_check_url;
    private String bottom_my_url;
    private String bottom_my_check_url;
    private String bottom_class_url;
    private String bottom_class_check_url;
    private String bottom_purchase_url;
    private String bottom_purchase_check_url;
    private String bottom_background_url;
    private String page_activity_url;
    private String page_replacement_order_url;
    private String page_movie_url;
    private String page_personal_center_url;

    private String words;
    private String words_check;

    private String create_date;

    public long getId () {
        return id;
    }

    public void setId (long id) {
        this.id = id;
    }

    public String getTemplate_name () {
        return template_name;
    }

    public void setTemplate_name (String template_name) {
        this.template_name = template_name;
    }

    public int getEnable_state () {
        return enable_state;
    }

    public void setEnable_state (int enable_state) {
        this.enable_state = enable_state;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getEffect_start_time () {
        return effect_start_time;
    }

    public void setEffect_start_time (Date effect_start_time) {
        this.effect_start_time = effect_start_time;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getEffect_end_time () {
        return effect_end_time;
    }

    public void setEffect_end_time (Date effect_end_time) {
        this.effect_end_time = effect_end_time;
    }

    public String getHead_general_url () {
        return head_general_url;
    }

    public void setHead_general_url (String head_general_url) {
        this.head_general_url = head_general_url;
    }

    public String getHead_activity_url () {
        return head_activity_url;
    }

    public void setHead_activity_url (String head_activity_url) {
        this.head_activity_url = head_activity_url;
    }

    public String getHead_mall_url () {
        return head_mall_url;
    }

    public void setHead_mall_url (String head_mall_url) {
        this.head_mall_url = head_mall_url;
    }

    public String getHead_movie_url () {
        return head_movie_url;
    }

    public void setHead_movie_url (String head_movie_url) {
        this.head_movie_url = head_movie_url;
    }

    public String getHead_replacement_order_url () {
        return head_replacement_order_url;
    }

    public void setHead_replacement_order_url (String head_replacement_order_url) {
        this.head_replacement_order_url = head_replacement_order_url;
    }

    public String getBottom_index_url () {
        return bottom_index_url;
    }

    public void setBottom_index_url (String bottom_index_url) {
        this.bottom_index_url = bottom_index_url;
    }

    public String getBottom_index_check_url () {
        return bottom_index_check_url;
    }

    public void setBottom_index_check_url (String bottom_index_check_url) {
        this.bottom_index_check_url = bottom_index_check_url;
    }

    public String getBottom_msg_url () {
        return bottom_msg_url;
    }

    public void setBottom_msg_url (String bottom_msg_url) {
        this.bottom_msg_url = bottom_msg_url;
    }

    public String getBottom_msg_check_url () {
        return bottom_msg_check_url;
    }

    public void setBottom_msg_check_url (String bottom_msg_check_url) {
        this.bottom_msg_check_url = bottom_msg_check_url;
    }

    public String getBottom_my_url () {
        return bottom_my_url;
    }

    public void setBottom_my_url (String bottom_my_url) {
        this.bottom_my_url = bottom_my_url;
    }

    public String getBottom_my_check_url () {
        return bottom_my_check_url;
    }

    public void setBottom_my_check_url (String bottom_my_check_url) {
        this.bottom_my_check_url = bottom_my_check_url;
    }

    public String getBottom_class_url () {
        return bottom_class_url;
    }

    public void setBottom_class_url (String bottom_class_url) {
        this.bottom_class_url = bottom_class_url;
    }

    public String getBottom_class_check_url () {
        return bottom_class_check_url;
    }

    public void setBottom_class_check_url (String bottom_class_check_url) {
        this.bottom_class_check_url = bottom_class_check_url;
    }

    public String getBottom_purchase_url () {
        return bottom_purchase_url;
    }

    public void setBottom_purchase_url (String bottom_purchase_url) {
        this.bottom_purchase_url = bottom_purchase_url;
    }

    public String getBottom_purchase_check_url () {
        return bottom_purchase_check_url;
    }

    public void setBottom_purchase_check_url (String bottom_purchase_check_url) {
        this.bottom_purchase_check_url = bottom_purchase_check_url;
    }

    public String getBottom_background_url () {
        return bottom_background_url;
    }

    public void setBottom_background_url (String bottom_background_url) {
        this.bottom_background_url = bottom_background_url;
    }

    public String getCreate_date () {
        return create_date;
    }

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat (pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public void setCreate_date (String create_date) {
        this.create_date = create_date;
    }

    public String getPage_activity_url() {
        return page_activity_url;
    }

    public void setPage_activity_url(String page_activity_url) {
        this.page_activity_url = page_activity_url;
    }

    public String getPage_replacement_order_url() {
        return page_replacement_order_url;
    }

    public void setPage_replacement_order_url(String page_replacement_order_url) {
        this.page_replacement_order_url = page_replacement_order_url;
    }

    public String getPage_movie_url() {
        return page_movie_url;
    }

    public void setPage_movie_url(String page_movie_url) {
        this.page_movie_url = page_movie_url;
    }

    public String getPage_personal_center_url() {
        return page_personal_center_url;
    }

    public void setPage_personal_center_url(String page_personal_center_url) {
        this.page_personal_center_url = page_personal_center_url;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public String getWords_check() {
        return words_check;
    }

    public void setWords_check(String words_check) {
        this.words_check = words_check;
    }
}
