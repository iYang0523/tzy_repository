package com.jiyun.controller;

import com.jiyun.pojo.TbItem;
import com.jiyun.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/frontend/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @RequestMapping("/addItem")
    void addItem(@RequestParam("itemId")Long itemId,@RequestParam("userId") Long userId){
        cartService.addItem(itemId,userId);
    }
    @RequestMapping("/showCart")
    Collection<TbItem> showCart(@RequestParam("userId") Long userId){
      return   cartService.showCart(userId);
    }

    @RequestMapping("/updateItemNum")
    void updateItemNum(@RequestParam("itemId")Long itemId,@RequestParam("userId") Long userId,@RequestParam("num") Integer num){
        cartService.updateItemNum(itemId,userId,num);
    }

    @RequestMapping("/deleteItemFromCart")
    void deleteItemFromCart(@RequestParam("itemId")Long itemId,@RequestParam("userId") Long userId){
        cartService.deleteItemFromCart(itemId,userId);
    }
}
