package com.jiyun.controller;

import com.jiyun.pojo.TbItemCat;
import com.jiyun.service.ItemCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/backend/itemCategory")
public class ItemCategoryController {
    @Autowired
 private ItemCategoryService itemCategoryService;

    @PostMapping("selectItemCategoryByParentId")
  public  List<TbItemCat> selectItemCategoryByParentId(@RequestParam(value = "id") Integer id)
    {
return itemCategoryService.selectItemCategoryByParentId(id);
    };
}
