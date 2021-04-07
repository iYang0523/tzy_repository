package com.jiyun.controller;

import com.jiyun.fegin.ContentServiceFeign;

import com.jiyun.pojo.TbContent;
import com.jiyun.pojo.TbContentCategory;
import com.jiyun.pojo.TbItem;
import com.jiyun.utils.PageResult;
import com.jiyun.vo.ItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.jiyun.utils.Result;

import java.util.List;

@RestController
@RequestMapping("/backend/content")
public class ContentCategoryController {
    @Autowired
    private ContentServiceFeign contentServiceFeign;
    // 分类 查询
    @RequestMapping("/selectContentCategoryByParentId")
    public Result selectContentCategoryByParentId(@RequestParam(value = "id", defaultValue = "0") Long id){
     List<TbContentCategory> list= contentServiceFeign.selectContentCategoryByParentId(id);
        if (list != null && list.size() > 0) {
            return Result.ok(list);
        }
        return Result.error("查无结果");
    }

    // 添加
    @RequestMapping("/insertContentCategory")
    public Result insertContentCategory(TbContentCategory tbContentCategory){
      Integer num= contentServiceFeign.insertContentCategory(tbContentCategory);
      if(num==1){
          return Result.ok();
      }
      return Result.error("太久失败");
    }


    @RequestMapping("/deleteContentCategoryById")
    public Result deleteContentCategoryById(@RequestParam(value = "categoryId") Long categoryId){
        Integer num= contentServiceFeign.deleteContentCategoryById(categoryId);
        if(num==200){
            return Result.ok();
        }
        return Result.error("太久失败");
    }
    @RequestMapping("/updateContentCategory")
    public Result updateContentCategory(@RequestParam(value = "id") Long id,@RequestParam(value = "name") String name){
        contentServiceFeign.updateContentCategory(id,name);
            return Result.ok();
    }


    @RequestMapping("/selectTbContentAllByCategoryId")
    public Result selectTbContentAllByCategoryId(@RequestParam(value = "page",defaultValue = "1") Integer page,@RequestParam(value = "rows",defaultValue = "30") Integer rows,@RequestParam(value = "categoryId") Long categoryId) {

        PageResult pageResult = contentServiceFeign.selectTbContentAllByCategoryId(page, rows,categoryId);
        if(pageResult!=null && pageResult.getResult().size()>0){
            return Result.ok(pageResult);
        }
        return Result.error("查无结果");
    }
    @RequestMapping("/insertTbContent")
    public Result insertTbContent(TbContent tbContent){
        contentServiceFeign.insertTbContent(tbContent);
        return Result.ok();
    }
    @RequestMapping("/deleteContentByIds")
    public Result deleteContentByIds(@RequestParam(value = "ids") Long ids){
        contentServiceFeign.deleteContentByIds(ids);
        return Result.ok();
    }


}
