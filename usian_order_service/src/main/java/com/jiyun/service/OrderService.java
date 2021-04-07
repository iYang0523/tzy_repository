package com.jiyun.service;

import com.jiyun.RedisClient.RedisClient;
import com.jiyun.mapper.TbItemMapper;
import com.jiyun.mapper.TbOrderItemMapper;
import com.jiyun.mapper.TbOrderMapper;
import com.jiyun.mapper.TbOrderShippingMapper;
import com.jiyun.pojo.TbOrder;
import com.jiyun.pojo.TbOrderItem;
import com.jiyun.pojo.TbOrderShipping;
import com.jiyun.utils.JsonUtils;
import com.jiyun.vo.OrderVo;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class OrderService {
    @Autowired
    private RedisClient redisClient;
    @Autowired
    private TbOrderMapper tbOrderMapper;
    @Autowired
    private TbOrderItemMapper tbOrderItemMapper;
    @Autowired
    private TbOrderShippingMapper tbOrderShippingMapper;
@Autowired
private AmqpTemplate amqpTemplate;
    @Autowired
    private TbItemMapper tbItemMapper;
    /*
    * 生成订单
    * */
    @Transactional
    public String insertOrder(OrderVo orderVo) {
    //新增 订单表 order
        TbOrder tbOrder = orderVo.getTbOrder();
        //订单号  项目号+时间+序列号(redis)+机器号
        if(!redisClient.exists("ORDER_ID")){
            redisClient.set("ORDER_ID",1000);
        }
        Date now = new Date();
        String orderId="007"+now.getTime()+redisClient.incr("ORDER_ID",1);
        tbOrder.setOrderId(orderId);
        tbOrder.setStatus(1);
        tbOrder.setCreateTime(now);

          tbOrderMapper.insertSelective(tbOrder);

        // 订单项表  order item
        String orderItemStr = orderVo.getOrderItem();
        List<TbOrderItem> orderItems = JsonUtils.jsonToList(orderItemStr, TbOrderItem.class);

        for(TbOrderItem orderItem : orderItems){
            orderItem.setId(UUID.randomUUID().toString());
            orderItem.setOrderId(orderId);
            tbOrderItemMapper.insertSelective(orderItem);
        }

        // 收货地址  order shipping
        TbOrderShipping orderShipping = orderVo.getOrderShipping();
        orderShipping.setOrderId(orderId);
        orderShipping.setCreated(now);
        tbOrderShippingMapper.insertSelective(orderShipping);

    /*
    * 发送方 ：orderService  当前位置
    *  接收方： ItemService
    * 消息 list<Map<Object,Object>>
    * */
        List<Map<Object,Object>> message=new ArrayList<>();
        for(TbOrderItem orderItem : orderItems){
            orderItem.setId(UUID.randomUUID().toString());
            orderItem.setOrderId(orderId);
          HashMap<Object,Object> item=new HashMap<>();
          item.put("itemId",orderItem.getItemId());
          item.put("num",orderItem.getNum());
          message.add(item);
        }

   amqpTemplate.convertAndSend("order_item","update_num");
        return  orderId;
    }
}
