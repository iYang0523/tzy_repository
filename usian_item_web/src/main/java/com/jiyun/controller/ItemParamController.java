package com.jiyun.controller;

import com.jiyun.feign.ItemServiceFeign;
import com.jiyun.pojo.TbItemCat;
import com.jiyun.pojo.TbItemParam;
import com.jiyun.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/backend/itemParam")
public class ItemParamController {

    @Autowired
    private ItemServiceFeign itemServiceFeign;

    @RequestMapping("/selectItemParamByItemCatId/{itemCatId}")
    public Result selectItemParamByItemCatId(@PathVariable("itemCatId") Integer itemCatId){
     try {
     TbItemParam itemParam= itemServiceFeign.selectItemParamByItemCatId(itemCatId);
     return Result.ok(itemParam);
     }catch (Exception e){
         e.printStackTrace();
         return  Result.error(e.getMessage());
     }
    }
}
