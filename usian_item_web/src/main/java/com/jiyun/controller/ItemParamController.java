package com.jiyun.controller;

import com.jiyun.feign.ItemServiceFeign;
import com.jiyun.pojo.TbItem;
import com.jiyun.pojo.TbItemCat;
import com.jiyun.pojo.TbItemParam;
import com.jiyun.pojo.TbItemParamItem;
import com.jiyun.utils.PageResult;
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
    //规格模板查询接口
    @RequestMapping("/selectItemParamAll")
    public Result selectItemParamAll(@RequestParam(value = "page",defaultValue = "1") Integer page,@RequestParam(value = "rows",defaultValue = "2") Integer rows){
        try {
            PageResult<TbItemParam> pageInfo= itemServiceFeign.selectItemParamAll(page,rows);
            return Result.ok(pageInfo);
        }catch (Exception e){
            e.printStackTrace();
            return Result.error("失败");
        }
    }

    // 规格模板删除接口
    @RequestMapping("/deleteItemParamById")
    public Result deleteItemParamById(@RequestParam("id")Long id){
        try {
            itemServiceFeign.deleteItemParamById(id);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(e.getMessage());
        }
    }
 //规格模板的添加
    @RequestMapping("/insertItemParam")
    public Result insertItemParam(@RequestParam("itemCatId")Long itemCatId,@RequestParam("paramData")String paramData){
        Integer num=itemServiceFeign.insertItemParam(itemCatId,paramData);
          if(num==1){
            return Result.ok();
        }
        return Result.error("添加失败，该类目已有规格模板");
    }

}
