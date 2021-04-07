package com.jiyun.controller;

import com.jiyun.fegin.ContentServiceFeign;
import com.jiyun.feign.ItemServiceFeign;
import com.jiyun.pojo.TbItem;
import com.jiyun.pojo.TbItemDesc;
import com.jiyun.pojo.TbItemParamItem;
import com.jiyun.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/frontend/detail")
public class ItemDetailController {

    /*
    * 查询商品基本信息
    * */
    @Autowired
    private ContentServiceFeign contentServiceFeign;

    /**
     * 查询商品基本信息
     */
    @RequestMapping("/selectItemInfo")
    public Result selectItemInfo(@RequestParam("itemId") Long itemId) {
        TbItem tbItem = contentServiceFeign.selectItemInfo(itemId);
        if (tbItem != null) {
            return Result.ok(tbItem);
        }
        return Result.error("查无结果");
    }

    /**
     * 查询商品介绍
     */
    @RequestMapping("/selectItemDescByItemId")
    public Result selectItemDescByItemId(@RequestParam("itemId") Long itemId){
        TbItemDesc tbItemDesc = contentServiceFeign.selectItemDescByItemId(itemId);
        if(tbItemDesc!= null){
            return Result.ok (tbItemDesc);
        }
        return Result.error ("查无结果");
    }

    /**
     * 根据商品 ID 查询商品规格参数
     */
    @RequestMapping("/selectTbItemParamItemByItemId")
    public Result selectTbItemParamItemByItemId(@RequestParam("itemId") Long itemId){
        TbItemParamItem tbItemParamItem =
                contentServiceFeign.selectTbItemParamItemByItemId(itemId);
        if(tbItemParamItem != null){
            return Result.ok(tbItemParamItem);
        }
        return Result. error ("查无结果");
    }
}
