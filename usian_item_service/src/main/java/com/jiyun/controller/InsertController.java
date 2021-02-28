package com.jiyun.controller;

import com.jiyun.service.InserService;
import com.jiyun.service.ItemService;
import com.jiyun.vo.ItemVo;
import com.jiyun.vo.ItenUpdateIVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/backend/item")
public class InsertController {
    @Autowired
    private InserService inserService;

    @RequestMapping("/insertTbItem")
    void insertTbItem(@RequestBody ItemVo itemVo) {
        inserService.insertTbItem(itemVo);
    }

    @RequestMapping("/preUpdateItem")
    public ItenUpdateIVo preUpdateItem(@RequestParam("itemId") Long itemId) {
        return inserService.preUpdateItem(itemId);
    }

    @RequestMapping("/deleteItemById")
    void deleteItemById(@RequestParam("itemId")Long itemId){
        inserService.deleteItemById(itemId);
    }

    @RequestMapping("/updateTbItem")
    void updateTbItem(@RequestBody ItemVo itemVo) {
        inserService.updateTbItem(itemVo);
    }
}