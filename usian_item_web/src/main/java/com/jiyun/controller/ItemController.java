package com.jiyun.controller;

import com.jiyun.feign.ItemServiceFeign;
import com.jiyun.pojo.TbItem;
import com.jiyun.utils.PageResult;
import com.jiyun.vo.ItemVo;
import com.jiyun.vo.ItenUpdateIVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jiyun.utils.Result;
@RestController
@RequestMapping("/backend/item")
public class ItemController {
    @Autowired
    private ItemServiceFeign itemServiceFeign;
// 删除
@RequestMapping("/deleteItemById")
public Result deleteItemById(@RequestParam("itemId")Long itemId){
    try {
        itemServiceFeign.deleteItemById(itemId);
        return Result.ok();
    } catch (Exception e) {
        e.printStackTrace();
        return Result.error(e.getMessage());
    }
}
  // 添加
    @RequestMapping("/insertTbItem")
    public Result insertTbItem(ItemVo itemVo){
       itemServiceFeign.insertTbItem(itemVo);
            return Result.ok();
    }
    // 修改查询
    @RequestMapping("/preUpdateItem")
    public Result preUpdateItem(@RequestParam("itemId") Long itemId){
        ItenUpdateIVo itenUpdateIVo=  itemServiceFeign.preUpdateItem(itemId);
        return Result.ok(itenUpdateIVo);
    }
    // 修改
    @RequestMapping("/updateTbItem")
    public Result updateTbItem(ItemVo itemVo){
        itemServiceFeign.updateTbItem(itemVo);
        return Result.ok();
    }

    /**
     * 查询商品基本信息
     */
    @RequestMapping("/selectItemInfo")
    public Result selectItemInfo(Long itemId) {
        TbItem tbItem = itemServiceFeign.selectItemInfo(itemId);
        if (tbItem != null) {
            return Result.ok(tbItem);
        }
        return Result.error("查无结果");
    }
    @GetMapping("/selectTbItemAllByPage")
    public Result selectTbItemAllByPage(@RequestParam(value = "page",defaultValue = "1") Integer page,@RequestParam(value = "rows",defaultValue = "2") Integer rows){
      try {
          PageResult<TbItem> pageInfo= itemServiceFeign.selectTbItemAllByPage(page,rows);
          return Result.ok(pageInfo);
      }catch (Exception e){
          e.printStackTrace();
          return Result.error("失败");
      }
    }
}
