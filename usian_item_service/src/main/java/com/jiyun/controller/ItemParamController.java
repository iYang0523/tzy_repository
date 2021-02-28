package com.jiyun.controller;

import com.jiyun.pojo.TbItemParam;
import com.jiyun.service.ItemParamService;
import com.jiyun.vo.ItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/backend/itemParam")
public class ItemParamController {
@Autowired
    private ItemParamService itemParamService;

    @RequestMapping("/selectItemParamByItemCatId/{itemCatId}")
    public TbItemParam selectItemParamByItemCatId(@PathVariable("itemCatId") Integer itemCatId){
        return   itemParamService.selectItemParamByItemCatId(itemCatId);
    }


}
