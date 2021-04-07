package com.jiyun.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiyun.RedisClient.RedisClient;
import com.jiyun.mapper.TbItemDescMapper;
import com.jiyun.mapper.TbItemMapper;
import com.jiyun.mapper.TbItemParamItemMapper;
import com.jiyun.pojo.*;
import com.jiyun.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ItemService {
    @Autowired
    private TbItemMapper tbItemMapper;

//    @Autowired
//    private RedisClient redisClient;
//@Autowired
//private TbItemDescMapper tbItemDescMapper;
//@Autowired
//private TbItemParamItemMapper tbItemParamItemMapper;


    public TbItem selectItemInfo(Long itemId) {
//        /*从缓存中查*/
//        TbItem item = (TbItem) redisClient.get("ITEM_INFO" + ":" + itemId + ":"+ "BASE");
//        if (item!=null){
//            return item;
//        }
//
//        if (redisClient.setnx("ITEM_INFO_LOCK","xx",1)){
//            item= tbItemMapper.selectByPrimaryKey(itemId);
//            if (item==null){
////            把数据保存到缓存
//                redisClient.set("ITEM_INFO" + ":" + itemId + ":"+ "BASE",new TbItem());
//                redisClient.expire("ITEM_INFO" + ":" + itemId + ":"+ "BASE",30);
//            }else {
//                //把数据保存到缓存
//                redisClient.set("ITEM_INFO" + ":" + itemId + ":"+ "BASE",item);
//                //设置缓存的有效期
//                redisClient.expire("ITEM_INFO" + ":" + itemId + ":"+ "BASE",2*24*60*60);
//            }
//            /*释放锁*/
//            redisClient.del("ITEM_INFO_LOCK");
//        }
//
//
//
//        return item;

        return tbItemMapper.selectByPrimaryKey(itemId);
    }
    // page 第几页
    // rows 每页多少条数据
    public PageResult<TbItem> selectTbItemAllByPage(Integer page, Integer rows) {
        PageHelper.startPage(page,rows);// 分页
        TbItemExample example = new TbItemExample(); //设置各种查询条件的
        example.setOrderByClause("created desc");
        //添加条件
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andStatusNotEqualTo((byte)3);

        List<TbItem> tbItems = tbItemMapper.selectByExample(example);
        PageInfo<TbItem> pageInfo = new PageInfo<>(tbItems);  //页的结果集对象 --》 pageHepler的类型

        PageResult<TbItem> pageResult = new PageResult<>(page,pageInfo.getPages(),tbItems);
        return pageResult;
    }

//    public TbItemDesc selectItemDescByItemId(Long itemId) {
//        //查询缓存
//        TbItemDesc tbItemDesc1 = (TbItemDesc) redisClient.get(
//                "ITEM_INFO" + ":" + itemId + ":"+ "DESC");
//        if(tbItemDesc1!=null){
//            return tbItemDesc1;
//        }
//        if (redisClient.setnx("SETNX_DESC_LOCK_KEY","xx",1)){
//            TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);
//            if (tbItemDesc != null) {
//                redisClient.set("ITEM_INFO" + ":" + itemId + ":" + "DESC", tbItemDesc);
//                //设置缓存的有效期
//                redisClient.expire("ITEM_INFO" + ":" + itemId + ":" + "DESC", 2 * 24 * 60 * 60);
//                return tbItemDesc;
//            } else {
//                redisClient.set("ITEM_INFO" + ":" + itemId + ":" + "DESC", null);
//                //设置缓存的有效期
//                redisClient.expire("ITEM_INFO" + ":" + itemId + ":" + "DESC", 30);
//            }
//            /*释放锁*/
//            redisClient.del("SETNX_DESC_LOCK_KEY");
//        }
//        return null;
//    }
//
//    public TbItemParamItem selectTbItemParamItemByItemId(Long itemId) {
//        //查询缓存
//        TbItemParamItem tbItemParamItem = (TbItemParamItem) redisClient.get(
//                "ITEM_INFO" + ":" + itemId + ":"+ "PARAM");
//        if(tbItemParamItem!=null){
//            return tbItemParamItem;
//        }
//        if (redisClient.setnx("SETNX_PARAM_LOCK_KEY","xx",1)) {
//
//            TbItemParamItemExample tbItemParamItemExample = new TbItemParamItemExample();
//            TbItemParamItemExample.Criteria criteria = tbItemParamItemExample.createCriteria();
//            criteria.andItemIdEqualTo(itemId);
//
//            List<TbItemParamItem> tbItemParamItems = tbItemParamItemMapper.selectByExampleWithBLOBs(tbItemParamItemExample);
//            if (tbItemParamItems != null && tbItemParamItems.size() > 0) {
//                //把数据保存到缓存
//                redisClient.set("ITEM_INFO" + ":" + itemId + ":" +
//                        "PARAM", tbItemParamItems.get(0));
//                //设置缓存的有效期
//                redisClient.expire("ITEM_INFO" + ":" + itemId + ":" + "PARAM", 2 * 24 * 60 * 60);
//                return tbItemParamItems.get(0);
//            } else {
//                //把数据保存到缓存
//                redisClient.set("ITEM_INFO" + ":" + itemId + ":" + "PARAM", null);
//                //设置缓存的有效期
//                redisClient.expire("ITEM_INFO" + ":" + itemId + ":" + "PARAM", 30);
//            }
//            /*释放锁*/
//            redisClient.del("SETNX_PARAM_LOCK_KEY");
//        }
//
//        return null;
//    }

    public void updateNumById(List<Map> items){
    // 扣除库存
    /*
     * 商品中的库存，扣除当前订单中商品购物的数量
     *
     *  遍历订单项，每一项扣除库存
     *
     * */
        for(Map orderItem : items){
        String itemId = (String) orderItem.get("itemId");// 购买商品的ID
        Integer num = (Integer) orderItem.get("num");  //购买的数量
        // 减库存 update  item set num=num-${num}  where id=${itemId}
        tbItemMapper.updateNumByID(Long.valueOf(itemId),num);
    }
    }
}
