package com.jiyun.feign;

import com.jiyun.pojo.TbItem;
import com.jiyun.vo.OrderVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

@FeignClient(value = "usian-cart-service")
public interface CartServiceFeign {

    @RequestMapping("/frontend/cart/addItem")
    void addItem(@RequestParam("itemId")Long itemId,@RequestParam("userId") Long userId);
@RequestMapping("/frontend/cart/showCart")
Collection<TbItem> showCart(@RequestParam("userId") Long userId);
@RequestMapping("/frontend/cart/updateItemNum")
    void updateItemNum(@RequestParam("itemId")Long itemId,@RequestParam("userId") Long userId,@RequestParam("num") Integer num);
@RequestMapping("/frontend/cart/deleteItemFromCart")
    void deleteItemFromCart(@RequestParam("itemId")Long itemId,@RequestParam("userId") Long userId);

}
