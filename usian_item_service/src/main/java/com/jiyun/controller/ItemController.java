package com.jiyun.controller;

import com.jiyun.pojo.TbItem;
import com.jiyun.service.ItemService;
import com.jiyun.utils.PageResult;
import com.jiyun.vo.ItenUpdateIVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    /**查询商品信息
     * 根据商品id
     * @param itemId
     * @return
     */
    @RequestMapping("/selectItemInfo")
    public TbItem selectItemInfo(Long itemId){
        return this.itemService.selectItemInfo(itemId);
    }
    @RequestMapping("/selectTbItemAllByPage")
  public  PageResult<TbItem> selectTbItemAllByPage(@RequestParam(value = "page") Integer page, @RequestParam(value = "rows") Integer rows){
return  itemService.selectTbItemAllByPage(page,rows);
    }

    }


