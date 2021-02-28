package com.jiyun.feign;

import com.jiyun.pojo.TbItem;
import com.jiyun.pojo.TbItemCat;
import com.jiyun.pojo.TbItemParam;
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

@PostMapping("/backend/itemCategory/selectItemCategoryByParentId")
    List<TbItemCat> selectItemCategoryByParentId(@RequestParam(value = "id") Integer id);

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
}
