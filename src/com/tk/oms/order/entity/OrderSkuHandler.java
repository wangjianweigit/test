package com.tk.oms.order.entity;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.druid.proxy.jdbc.ConnectionProxyImpl;

import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

/**
 * 
 * Copyright (c), 2018, Tongku
 * FileName : OrderSkuHandler.java
 * 
 *
 * @author yejingquan
 * @version 1.00
 * @date 2018年8月20日
 */
@SuppressWarnings("rawtypes")
public class OrderSkuHandler extends BaseTypeHandler{

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Object o, JdbcType jdbcType) throws SQLException {

        DruidPooledConnection connection = (DruidPooledConnection) preparedStatement.getConnection();

        Connection conn = connection.getConnection();

        if(conn instanceof ConnectionProxyImpl) {
            conn = ((ConnectionProxyImpl) conn).getConnectionRaw();
        }

        List<OrderProductSku> listData = (ArrayList<OrderProductSku>) o;

        ARRAY array = getArray(conn, "T_ORDER_PRODUCT_SKU", "T_ORDER_PRODUCT_SKU_LST", listData);
        preparedStatement.setArray(i, array);
    }

    @Override
    public Object getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return null;
    }

    @Override
    public Object getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return null;
    }

    @Override
    public Object getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }

    private ARRAY getArray(Connection conn, String oracleObj, String oracleList, List<OrderProductSku> listData) throws SQLException {
        ARRAY array = null;

        ArrayDescriptor arrayDescriptor = ArrayDescriptor.createDescriptor(oracleList, conn);
        STRUCT[] structs = new STRUCT[0];
        if(listData != null && listData.size() > 0) {
            structs = new STRUCT[listData.size()];
            StructDescriptor structDescriptor = new StructDescriptor(oracleObj, conn);
            for(int i=0; i<listData.size(); i++) {
            	OrderProductSku sku = listData.get(i);
                Object[] result = {sku.getSku_id(),sku.getOrder_old_prize(),sku.getOrder_new_prize(),sku.getOrder_discount_money()};
                structs[i] = new STRUCT(structDescriptor, conn, result);
            }
        }

        array = new ARRAY(arrayDescriptor, conn, structs);

        return array;
    }
}
