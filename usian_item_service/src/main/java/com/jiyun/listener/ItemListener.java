package com.jiyun.listener;

import com.jiyun.service.ItemService;
import com.jiyun.utils.PageResult;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.ws.soap.Addressing;
import java.util.List;
import java.util.Map;

@Component
public class ItemListener {
    @Autowired
    private ItemService itemService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value="item_num_queue",durable = "true"),
            exchange = @Exchange(value="order_item",type= ExchangeTypes.TOPIC),
            key= {"update_num"}
    ))
    public void updateNumById(List<Map> items){
        itemService.updateNumById(items);
    }
}
