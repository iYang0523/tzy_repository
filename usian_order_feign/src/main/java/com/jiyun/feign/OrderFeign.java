package com.jiyun.feign;


import com.jiyun.vo.OrderVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;



@FeignClient(value = "usian-order-service")
public interface OrderFeign {
    @RequestMapping("/frontend/order/insertOrder")
    String insertOrder(@RequestBody OrderVo orderVo);
}
