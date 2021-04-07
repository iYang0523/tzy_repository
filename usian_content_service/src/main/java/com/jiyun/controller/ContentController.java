package com.jiyun.controller;


import com.jiyun.pojo.TbItem;
import com.jiyun.pojo.TbItemDesc;
import com.jiyun.pojo.TbItemParamItem;
import com.jiyun.service.ContentService;
import com.jiyun.utils.AdNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/frontend/content")
public class ContentController {
    @Autowired
    private ContentService contentService;
    @RequestMapping("/selectFrontendContentByAD")
    List<AdNode> selectFrontendContentByAD(){
     return  contentService.selectFrontendContentByAD();
    }


    @RequestMapping("/selectItemInfo")
    public TbItem selectItemInfo(@RequestParam("itemId") Long itemId){
        return this.contentService.selectItemInfo(itemId);
    }
    /**
     //     * 根据商品 ID 查询商品规格参数
     //     */
    @RequestMapping("/selectItemDescByItemId")
    public TbItemDesc selectItemDescByItemId(@RequestParam("itemId") Long itemId){
        return contentService.selectItemDescByItemId(itemId);
    }
//
//    /**
//     * 根据商品 ID 查询商品规格参数
//     */
    @RequestMapping("/selectTbItemParamItemByItemId")
    public TbItemParamItem selectTbItemParamItemByItemId(@RequestParam("itemId") Long itemId){
        return contentService.selectTbItemParamItemByItemId(itemId);
    }
}
