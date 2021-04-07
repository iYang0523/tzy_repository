package com.jiyun.controller;

import com.jiyun.feign.CartServiceFeign;
import com.jiyun.feign.OrderFeign;
import com.jiyun.pojo.*;
import com.jiyun.utils.Result;
import com.jiyun.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/frontend/order")
public class OrderController {
    @Autowired
    private CartServiceFeign cartServiceFeign;
    @Autowired
    private OrderFeign orderFeign;
    @RequestMapping("/goSettlement")
    public Result goSettlement(@RequestParam("ids") Long[] ids, @RequestParam("userId")Long userId, @RequestParam("token") String token){
         // 获取选中购买商品的，购物车项信息
     // List<TbItem> orderItems=orderFeign.goSettlement(ids,userId);
        ArrayList<TbItem> buyItems = new ArrayList<>();
        Collection<TbItem> tbItems = cartServiceFeign.showCart(userId);
        for(Long id : ids){
       for(TbItem item : tbItems){
          if(item.getId().equals(id)){
              buyItems.add(item);
              break;
          }
       }
        }
        return Result.ok(buyItems);
    }

    @RequestMapping("/insertOrder")
  public Result insertOrder(@RequestParam("orderItem") String orderItem, TbOrder tbOrder, TbOrderShipping tbOrderShipping){
        String orderId = orderFeign.insertOrder(new OrderVo(orderItem, tbOrderShipping, tbOrder));
        return Result.ok(orderId);
    }
}
