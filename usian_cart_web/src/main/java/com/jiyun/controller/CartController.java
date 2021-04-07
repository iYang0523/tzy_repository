package com.jiyun.controller;

import com.jiyun.feign.CartServiceFeign;
import com.jiyun.feign.ItemServiceFeign;
import com.jiyun.pojo.TbItem;
import com.jiyun.utils.CookieUtils;
import com.jiyun.utils.JsonUtils;
import com.jiyun.utils.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/frontend/cart")
public class CartController {
    @Autowired
    private ItemServiceFeign itemServiceFeign;
    @Autowired
    private CartServiceFeign cartServiceFeign;
    /**
     * 添加商品购物车
     */
    @RequestMapping("/addItem")
    public Result addItem(Long itemId, Long userId, @RequestParam(defaultValue = "1") Integer num, HttpServletRequest request, HttpServletResponse response)
    {
   if(userId==null){
       // 未登录  //没有用户名
      // 1.从cookie中查询商品列表。
     Map<String,TbItem> cart= getCaryFromCookie(request);

       // 2.添加商品到购物车
       addItemToCart(cart,itemId,num);
       //3. 把购物车商品列表写入cookie
       addClientCookie(request,response,cart);
   }else {
       // 登录  有用户名
       cartServiceFeign.addItem(itemId,userId);
   }
        return Result.ok();
    }




    /*
   *  把购车商品列表写入cookie
   * */
    private void addClientCookie(HttpServletRequest request, HttpServletResponse response, Map<String, TbItem> cart) {
        String cartJson = JsonUtils.objectToJson(cart);
        CookieUtils.setCookie(request,response,"CART_COOKIE_KEY",cartJson,604800,true);
    }

    /*
    * 将商品添加到购物车中
    * */
    private void addItemToCart(Map<String, TbItem> cart, Long itemId, Integer num) {
    //从购物车中取商品
        TbItem tbItem = cart.get(itemId.toString());
        if(tbItem!=null){
            //商品列表中存在该商品，商品数量相加。
            tbItem.setNum(tbItem.getNum()+num);
        }else {
            // 商品列表中不存在该商品，根据商品id查询商品信息并添加到购物车列表
          tbItem = itemServiceFeign.selectItemInfo(itemId);
          tbItem.setNum(num);
        }
        cart.put(itemId.toString(),tbItem);
    }

    /*
    * 获取购物车
    *
    * */
    private Map<String, TbItem> getCaryFromCookie(HttpServletRequest request) {
        String cartJson = CookieUtils.getCookieValue(request, "CART_COOKIE_KEY", true);
        if(StringUtils.isNotBlank(cartJson))
        {
           //购物车已存在
            Map<String,TbItem> map = JsonUtils.jsonToMap(cartJson, TbItem.class);
            return map;
        }
        //购物车不存在
        return  new HashMap<String,TbItem>();
    }



   /*
   * 查看购物车
   * */
    @RequestMapping("/showCart")
    public Result showCart(Long userId,HttpServletRequest request,HttpServletResponse response)
    {
        ArrayList<TbItem> list=null;
        if(userId==null){
            //在用户未登录的状态下
             list = new ArrayList<>();
            Map<String, TbItem> cary = getCaryFromCookie(request);
            Set<String> keySet = cary.keySet();
            for (String key:keySet){
                list.add(cary.get(key));
            }
        }else {
          //在用户已登录的状态
            return Result.ok(cartServiceFeign.showCart(userId));
        }
      return Result.ok(list);
    }

    /*
    * 修改购物车
    * */
   @RequestMapping("/updateItemNum")
    public Result updateItemNum(Long userId,Long itemId,Integer num,
                                HttpServletRequest request,HttpServletResponse response){
   if(userId==null){
   // 未登录
    //1.获得cookie中的购物车
       Map<String, TbItem> cart = getCaryFromCookie(request);
     //2. 修改购物车中的商品
       TbItem tbItem = cart.get(itemId.toString());
       tbItem.setNum(num);
       cart.put(itemId.toString(),tbItem);
       //3.把购物车写到cookie
       addClientCookie(request,response,cart);
   }else {
 // 登录
       cartServiceFeign.updateItemNum(itemId,userId,num);
   }
return Result.ok();
   }

   /*
   * 删除购物车
   * */
    @RequestMapping("/deleteItemFromCart")
    public Result deleteItemFromCart(Long itemId,Long userId,HttpServletRequest request,HttpServletResponse response){
        if(userId==null){
            // 用户未登录
            //1.获得cookie中的购物车
            Map<String, TbItem> cart = getCaryFromCookie(request);
            //2. 修改购物车中的商品
           cart.remove(itemId.toString());
            //3.把购物车写到cookie
            addClientCookie(request,response,cart);
        }else {
            cartServiceFeign.deleteItemFromCart(itemId,userId);
        }
        return Result.ok();
    }
}
