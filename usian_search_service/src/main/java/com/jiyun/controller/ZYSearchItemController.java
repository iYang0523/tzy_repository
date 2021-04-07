//package com.jiyun.controller;
//
//import com.jiyun.Service.ZYSearchItemService;
//import com.jiyun.vo.ItemSearchVo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//import java.util.List;
//
///**
// * @author Abandon
// * @description SearchItemController
// * @date 2021/3/10 9:10
// */
//@RestController
//@RequestMapping("/frontend/searchItem")
//public class ZYSearchItemController {
//    @Autowired
//    private ZYSearchItemService searchItemService;
//
//    /*
//    *一键导入商品到索引库
//    * */
//    @RequestMapping("/importAll")
//    public boolean importAll(){
//        return searchItemService.importAll();
//    }
//    /*
//    * 商品搜索
//    * */
//    @PostMapping("/list")
//    public List<ItemSearchVo> list(@RequestParam String q, @RequestParam Integer page,
//                                   @RequestParam Integer pageSize){
//        return searchItemService.list(q,page,pageSize);
//    }
//}
