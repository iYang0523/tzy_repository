package com.jiyun.vo;

import com.jiyun.pojo.TbItem;

public class ItenUpdateIVo {
    private String  itemCat; // 类目的名称
    private TbItem item;//商品基本信息
    private String itemDesc; //商品描述
    private  String itemParamItem; //规格参数值

    public String getItemCat() {
        return itemCat;
    }

    public void setItemCat(String itemCat) {
        this.itemCat = itemCat;
    }

    public TbItem getItem() {
        return item;
    }

    public void setItem(TbItem item) {
        this.item = item;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public String getItemParamItem() {
        return itemParamItem;
    }

    public void setItemParamItem(String itemParamItem) {
        this.itemParamItem = itemParamItem;
    }
}
