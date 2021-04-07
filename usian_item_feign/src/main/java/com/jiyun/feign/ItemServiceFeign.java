package com.jiyun.feign;

import com.jiyun.pojo.*;
import com.jiyun.utils.CatResult;
import com.jiyun.utils.PageResult;
import com.jiyun.utils.Result;
import com.jiyun.vo.ItemVo;
import com.jiyun.vo.ItenUpdateIVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "usian-item-service")
public interface ItemServiceFeign {

    @RequestMapping("/service/item/selectItemInfo")
    TbItem selectItemInfo(@RequestParam("itemId") Long itemId);

@RequestMapping("/service/item/selectTbItemAllByPage")
   PageResult<TbItem> selectTbItemAllByPage(@RequestParam(value = "page") Integer page, @RequestParam(value = "rows") Integer rows);

@RequestMapping("/backend/itemCategory/selectItemCategoryByParentId")
    List<TbItemCat> selectItemCategoryByParentId(@RequestParam("id") Integer id);

@RequestMapping("/backend/itemParam/selectItemParamByItemCatId/{itemCatId}")
    TbItemParam selectItemParamByItemCatId(@PathVariable("itemCatId") Integer itemCatId);

    @RequestMapping("/backend/item/insertTbItem")
    public  void insertTbItem(@RequestBody ItemVo itemVo);

    @RequestMapping("/backend/item/preUpdateItem")
   ItenUpdateIVo preUpdateItem(@RequestParam("itemId") Long itemId);

    @RequestMapping("/backend/item/deleteItemById")
    void deleteItemById(@RequestParam("itemId")Long itemId);

    @RequestMapping("/backend/item/updateTbItem")
    void updateTbItem(ItemVo itemVo);


@RequestMapping("/backend/itemParam/selectItemParamAll")
    PageResult<TbItemParam> selectItemParamAll(@RequestParam(value = "page") Integer page, @RequestParam(value = "rows") Integer rows);

@RequestMapping("/backend/itemParam/deleteItemParamById")
    void deleteItemParamById(@RequestParam("id")Long id);

@RequestMapping("/backend/itemParam/insertItemParam")
    Integer insertItemParam(@RequestParam("itemCatId")Long itemCatId,@RequestParam("paramData")String paramData);


//
// @RequestMapping("/frontend/itemCategory/selectItemCategoryAll")
//     CatResult selectItemCategoryAll();


//    /**
//     * 查询商品介绍
//     */
//    @RequestMapping("/service/item/selectItemDescByItemId")
//    public TbItemDesc selectItemDescByItemId(@RequestParam("itemId") Long itemId);
//
//    /**
//     * 根据商品 ID 查询商品规格参数
//     */
//    @RequestMapping("/service/item/selectTbItemParamItemByItemId")
//    public TbItemParamItem selectTbItemParamItemByItemId(@RequestParam("itemId") Long itemId);


}
