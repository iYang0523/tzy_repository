package com.jiyun.controller;

import com.jiyun.pojo.TbContent;
import com.jiyun.pojo.TbContentCategory;
import com.jiyun.service.ContentCategoryService;
import com.jiyun.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/backend/content")
public class ContentCategoryController {
@Autowired
  private   ContentCategoryService contentCategoryService;
    @RequestMapping("/selectContentCategoryByParentId")
    List<TbContentCategory> selectContentCategoryByParentId(@RequestParam("id") Long id){
return contentCategoryService.selectContentCategoryByParentId(id);
    }

    @RequestMapping("/insertContentCategory")
    Integer insertContentCategory(@RequestBody TbContentCategory tbContentCategory){
     return    contentCategoryService.insertContentCategory(tbContentCategory);
    }

    @RequestMapping("/deleteContentCategoryById")
    Integer deleteContentCategoryById(@RequestParam("categoryId") Long categoryId){
        System.out.println(categoryId);
     return contentCategoryService.deleteContentCategoryById(categoryId);
    }

    @RequestMapping("/updateContentCategory")
    void updateContentCategory(@RequestParam(value = "id") Long id,@RequestParam(value = "name") String name){
        contentCategoryService.updateContentCategory(id,name);
    }

    @RequestMapping("/selectTbContentAllByCategoryId")
    PageResult  selectTbContentAllByCategoryId(@RequestParam(value = "page") Integer page,@RequestParam(value = "rows") Integer rows,@RequestParam(value = "categoryId") Long categoryId){
      return   contentCategoryService.selectTbContentAllByCategoryId(page,rows,categoryId);
    }
    @RequestMapping("/insertTbContent")
    void insertTbContent(@RequestBody TbContent tbContent){
        contentCategoryService.insertTbContent(tbContent);
    }

    @RequestMapping("/deleteContentByIds")
    void deleteContentByIds(@RequestParam(value = "ids") Long ids){
        contentCategoryService.deleteContentByIds(ids);
    }
}
