package com.jiyun.fegin;

import com.jiyun.pojo.*;
import com.jiyun.utils.AdNode;
import com.jiyun.utils.CatResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import  com.jiyun.utils.PageResult;
import java.util.List;

@FeignClient(value = "usian-content-service")
public interface ContentServiceFeign {
    @RequestMapping("/backend/content/selectContentCategoryByParentId")
    List<TbContentCategory> selectContentCategoryByParentId(@RequestParam("id") Long id);

@RequestMapping("/backend/content/insertContentCategory")
    Integer insertContentCategory(@RequestBody TbContentCategory tbContentCategory);
@RequestMapping("/backend/content/deleteContentCategoryById")
    Integer deleteContentCategoryById(@RequestParam(value = "categoryId") Long categoryId);
@RequestMapping("/backend/content/updateContentCategory")
    void updateContentCategory(@RequestParam(value = "id") Long id,@RequestParam(value = "name") String name);
@RequestMapping("/backend/content/selectTbContentAllByCategoryId")
    PageResult  selectTbContentAllByCategoryId(@RequestParam(value = "page") Integer page,@RequestParam(value = "rows") Integer rows,@RequestParam(value = "categoryId") Long categoryId);
@RequestMapping("/backend/content/insertTbContent")
    void insertTbContent(@RequestBody TbContent tbContent);

@RequestMapping("/backend/content/deleteContentByIds")
    void deleteContentByIds(@RequestParam(value = "ids") Long ids);

@RequestMapping("/frontend/itemCategory/selectItemCategoryAll")
    CatResult selectItemCategoryAll();
//
@RequestMapping("/frontend/content/selectFrontendContentByAD")
    List<AdNode> selectFrontendContentByAD();



    @RequestMapping("/frontend/content/selectItemInfo")
    TbItem selectItemInfo(@RequestParam("itemId") Long itemId);

    /**
     * 查询商品介绍
     */
    @RequestMapping("/frontend/content/selectItemDescByItemId")
    public TbItemDesc selectItemDescByItemId(@RequestParam("itemId") Long itemId);

    /**
     * 根据商品 ID 查询商品规格参数
     */
    @RequestMapping("/frontend/content/selectTbItemParamItemByItemId")
    public TbItemParamItem selectTbItemParamItemByItemId(@RequestParam("itemId") Long itemId);

}
