package com.jiyun.controller;

import com.jiyun.fegin.SearchFeign;

import com.jiyun.utils.Result;
import com.jiyun.vo.ItemSearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/frontend/searchItem")
public class ItemSearchController {
 @Autowired
     public SearchFeign searchFeign;
/*
*  一键导入的功能
* */
    @RequestMapping("/importAll")
    public boolean importAll(){
        if(searchFeign.importAll()){
            return true;
        }else {
            return false;
        }
    }

/*
* 搜索功能
* */
    @RequestMapping("/list")
    public List<ItemSearchVo> selectByQ(String q,@RequestParam(defaultValue = "1")Integer page,
                                        @RequestParam(defaultValue = "20")Integer pageSize)
    {
        return searchFeign.selectByq(q,page,pageSize);
    }

}
