package com.jiyun.controller;



import com.jiyun.fegin.ContentServiceFeign;
import com.jiyun.utils.CatResult;
import com.jiyun.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/frontend/itemCategory")
public class PortalController {

    @Autowired
    private ContentServiceFeign contentServiceFeign;

    @RequestMapping("/selectItemCategoryAll")
 public Result selectItemCategoryAll() {
        CatResult catResult = contentServiceFeign.selectItemCategoryAll();
            return Result.ok(catResult);
    }

}
