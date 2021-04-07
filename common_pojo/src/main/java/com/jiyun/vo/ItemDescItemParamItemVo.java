package com.jiyun.vo;

import java.util.Stack;

/**
 * @author Abandon
 * @description ItemDescItemParamItemVo
 * @date 2021/3/11 18:32
 */
public class ItemDescItemParamItemVo {
    /*
    * 存储商品信息
    * */
    public static final String ITEM_INFO = "ITEM_INFO";

    /*
    *商品描述
    * */
    public static final String ITEM_DESC = "ITEM_DESC";
    /*
    * 商品规格模板参数
    * */
    public static final String ITEM_PARAM_ITEM = "ITEM_PARAM_ITEM";
    /*
    * 商品信息redis 独占锁
    * */
    public static final String ITEM_LOCK = "ITEM_LOCK";
    /*
     *商品描述独占锁
     * */
    public static final String ITEM_DESC_LOCK = "ITEM_DESC_LOCK";
    /*
     * 商品规格模板参数独占锁
     * */
    public static final String ITEM_PARAM_ITEM_LOCK = "ITEM_PARAM_ITEM_LOCK";
}
