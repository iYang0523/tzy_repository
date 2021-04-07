package com.jiyun.controller;

import com.jiyun.pojo.TbItemParam;
import com.jiyun.service.ItemParamService;
import com.jiyun.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/backend/itemParam")
public class ItemParamController {
@Autowired
    private ItemParamService itemParamService;

    @RequestMapping("/selectItemParamByItemCatId/{itemCatId}")
    public TbItemParam selectItemParamByItemCatId(@PathVariable("itemCatId") Integer itemCatId){
        return   itemParamService.selectItemParamByItemCatId(itemCatId);
    }

    @RequestMapping("/selectItemParamAll")
    PageResult<TbItemParam> selectItemParamAll(@RequestParam(value = "page") Integer page, @RequestParam(value = "rows") Integer rows){
        return  itemParamService.selectItemParamAll(page,rows);
    }
    @RequestMapping("/deleteItemParamById")
    void deleteItemParamById(@RequestParam("id")Long id){
          itemParamService.deleteItemParamById(id);
    }

    @RequestMapping("/insertItemParam")
    Integer insertItemParam(@RequestParam("itemCatId")Long itemCatId,@RequestParam("paramData") String paramData){
      return   itemParamService.insertItemParam(itemCatId,paramData);
    }

}
