package com.jiyun.vo;

import com.jiyun.pojo.TbItem;

public class ItemVo extends TbItem {
    private String desc; // 描述
    private String itemParams; // 规格参数值

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getItemParams() {
        return itemParams;
    }

    public void setItemParams(String itemParams) {
        this.itemParams = itemParams;
    }
}
