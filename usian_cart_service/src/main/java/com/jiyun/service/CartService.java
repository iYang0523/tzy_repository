package com.jiyun.service;

import com.jiyun.RedisClient.RedisClient;
import com.jiyun.feign.ItemServiceFeign;
import com.jiyun.pojo.TbItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

@Service
public class CartService {

    @Autowired
    private ItemServiceFeign itemServiceFeign;
    @Autowired
    private RedisClient redisClient;
    /*
     * 获取商品信息
     * 判断redis中是否存在该商品信息
     * 如果存在则数量加1
     * 不存在则查询商品信息 添加
     * */
    public void addItem(Long itemId, Long userId) {
        TbItem tbItem = (TbItem)redisClient.hget(userId + "CART_REDIS_KEY", itemId + "");
       if(tbItem==null){
           tbItem=itemServiceFeign.selectItemInfo(itemId);
           tbItem.setNum(1);
       }else {
           tbItem.setNum(tbItem.getNum()+1);
       }
       redisClient.hset(userId+"CART_REDIS_KEY",itemId+"",tbItem);
    }

    /*
     * 获取redis购物车中这个用户的所有商品
     * */
    public Collection<TbItem> showCart(Long userId) {
        Map<String,TbItem> hash=redisClient.getHash(userId +"CART_REDIS_KEY");
        Collection<TbItem> values = hash.values();
        return values;
    }


    public void updateItemNum(Long itemId, Long userId,Integer num) {
        TbItem hget =(TbItem) redisClient.hget(userId + "CART_REDIS_KEY", itemId + "");

        // 修改num的数量
        hget.setNum(num);
        redisClient.hset(userId+"CART_REDIS_KEY",itemId+"",hget);
    }

    public void deleteItemFromCart(Long itemId, Long userId) {
        redisClient.hdel(userId + "CART_REDIS_KEY" ,itemId+"");

    }
}
