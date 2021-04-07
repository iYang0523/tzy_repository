package com.jiyun.controller;

import com.jiyun.fegin.ContentServiceFeign;
import com.jiyun.utils.AdNode;
import com.jiyun.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/frontend/content")
public class ContentController {
    @Autowired
    private ContentServiceFeign contentServiceFeign;
    /*
    * 导航栏和大图片
    * */
    @RequestMapping("/selectFrontendContentByAD")
    public Result selectFrontendContentByAD() {
        List<AdNode> con=contentServiceFeign.selectFrontendContentByAD();
        return Result.ok(con);
    }
}
