package com.jiyun.vo;

import com.jiyun.pojo.TbOrder;
import com.jiyun.pojo.TbOrderShipping;

public class OrderVo {
    private String orderItem;
    private TbOrderShipping orderShipping;
    private TbOrder tbOrder;

    public OrderVo() {
    }

    public OrderVo(String orderItem, TbOrderShipping orderShipping, TbOrder tbOrder) {
        this.orderItem = orderItem;
        this.orderShipping = orderShipping;
        this.tbOrder = tbOrder;
    }

    public String getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(String orderItem) {
        this.orderItem = orderItem;
    }

    public TbOrderShipping getOrderShipping() {
        return orderShipping;
    }

    public void setOrderShipping(TbOrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }

    public TbOrder getTbOrder() {
        return tbOrder;
    }

    public void setTbOrder(TbOrder tbOrder) {
        this.tbOrder = tbOrder;
    }
}
