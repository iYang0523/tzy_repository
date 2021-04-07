package com.jiyun.controller;

import com.jiyun.feign.ItemServiceFeign;
import com.jiyun.pojo.TbItemCat;
import com.jiyun.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// id 父类目的ID
@RestController
@RequestMapping("/backend/itemCategory")
public class ItemCategoryController {
@Autowired
    private ItemServiceFeign itemServiceFeign;

    @PostMapping("/selectItemCategoryByParentId")
    public Result selectItemCategoryByParentId(@RequestParam(value = "id",defaultValue = "0") Integer id){
     List<TbItemCat> list= itemServiceFeign.selectItemCategoryByParentId(id);
  return Result.ok(list);
    }
}
