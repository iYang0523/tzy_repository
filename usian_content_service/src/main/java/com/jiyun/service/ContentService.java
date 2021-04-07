package com.jiyun.service;

import com.jiyun.RedisClient.RedisClient;
import com.jiyun.mapper.TbContentMapper;
import com.jiyun.mapper.TbItemDescMapper;
import com.jiyun.mapper.TbItemMapper;
import com.jiyun.mapper.TbItemParamItemMapper;
import com.jiyun.pojo.*;
import com.jiyun.utils.AdNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.jiyun.vo.ShitiLei.*;

@Service
public class ContentService {
    @Autowired
    private TbContentMapper tbContentMapper;
    @Autowired
    private RedisClient redisClient;
    @Autowired
    private TbItemMapper tbItemMapper;

@Autowired
private TbItemDescMapper tbItemDescMapper;
@Autowired
private TbItemParamItemMapper tbItemParamItemMapper;

    public List<AdNode> selectFrontendContentByAD() {
        //查询缓存
        List<AdNode> adNodeListRedis =
                (List<AdNode>)redisClient.hget("PORTAL_AD_KEY",AD_CATEGORY_ID.toString());
        if(adNodeListRedis!=null){
            return adNodeListRedis;
        }

       // 查询数据库
        TbContentExample tbContentExample = new TbContentExample();
        TbContentExample.Criteria criteria = tbContentExample.createCriteria();
        criteria.andCategoryIdEqualTo(AD_CATEGORY_ID);
        List<TbContent> list=tbContentMapper.selectByExample(tbContentExample);
        ArrayList<AdNode> adNodes = new ArrayList<>();
        for(TbContent tbContent:list){
            AdNode adNode = new AdNode();
            adNode.setSrc(tbContent.getPic());
            adNode.setSrcB(tbContent.getPic2());
            adNode.setHref(tbContent.getUrl());
            adNode.setHeight(AD_HEIGHT);
            adNode.setWidth(AD_WIDTH);
            adNode.setHeightB(AD_HEIGHTB);
            adNode.setWidthB(AD_WIDTHB);
            adNodes.add(adNode);
        }
        //添加到缓存
        redisClient.hset("PORTAL_AD_KEY",AD_CATEGORY_ID.toString(),adNodes);
        return adNodes;
    }

    public TbItemDesc selectItemDescByItemId(Long itemId) {
        //查询缓存
        TbItemDesc tbItemDesc1 = (TbItemDesc) redisClient.get(
                "ITEM_INFO" + ":" + itemId + ":"+ "DESC");
        if(tbItemDesc1!=null){
            return tbItemDesc1;
        }
        if (redisClient.setnx("SETNX_DESC_LOCK_KEY","xx",1)){
            TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);
            if (tbItemDesc != null) {
                redisClient.set("ITEM_INFO" + ":" + itemId + ":" + "DESC", tbItemDesc);
                //设置缓存的有效期
                redisClient.expire("ITEM_INFO" + ":" + itemId + ":" + "DESC", 2 * 24 * 60 * 60);
                return tbItemDesc;
            } else {
                redisClient.set("ITEM_INFO" + ":" + itemId + ":" + "DESC", null);
                //设置缓存的有效期
                redisClient.expire("ITEM_INFO" + ":" + itemId + ":" + "DESC", 30);
            }
            /*释放锁*/
            redisClient.del("SETNX_DESC_LOCK_KEY");
        }
        return null;

    }

    public TbItem selectItemInfo(Long itemId) {
        /*从缓存中查*/
        TbItem item = (TbItem) redisClient.get("ITEM_INFO" + ":" + itemId + ":"+ "BASE");
        if (item!=null){
            return item;
        }

        if (redisClient.setnx("ITEM_INFO_LOCK","xx",1)){
            item= tbItemMapper.selectByPrimaryKey(itemId);
            if (item==null){
//            把数据保存到缓存
                redisClient.set("ITEM_INFO" + ":" + itemId + ":"+ "BASE",new TbItem());
                redisClient.expire("ITEM_INFO" + ":" + itemId + ":"+ "BASE",30);
            }else {
                //把数据保存到缓存
                redisClient.set("ITEM_INFO" + ":" + itemId + ":"+ "BASE",item);
                //设置缓存的有效期
                redisClient.expire("ITEM_INFO" + ":" + itemId + ":"+ "BASE",2*24*60*60);
            }
            /*释放锁*/
            redisClient.del("ITEM_INFO_LOCK");
        }



        return item;

    }

    public TbItemParamItem selectTbItemParamItemByItemId(Long itemId) {
        //查询缓存
        TbItemParamItem tbItemParamItem = (TbItemParamItem) redisClient.get(
                "ITEM_INFO" + ":" + itemId + ":"+ "PARAM");
        if(tbItemParamItem!=null){
            return tbItemParamItem;
        }
        if (redisClient.setnx("SETNX_PARAM_LOCK_KEY","xx",1)) {

            TbItemParamItemExample tbItemParamItemExample = new TbItemParamItemExample();
            TbItemParamItemExample.Criteria criteria = tbItemParamItemExample.createCriteria();
            criteria.andItemIdEqualTo(itemId);

            List<TbItemParamItem> tbItemParamItems = tbItemParamItemMapper.selectByExampleWithBLOBs(tbItemParamItemExample);
            if (tbItemParamItems != null && tbItemParamItems.size() > 0) {
                //把数据保存到缓存
                redisClient.set("ITEM_INFO" + ":" + itemId + ":" +
                        "PARAM", tbItemParamItems.get(0));
                //设置缓存的有效期
                redisClient.expire("ITEM_INFO" + ":" + itemId + ":" + "PARAM", 2 * 24 * 60 * 60);
                return tbItemParamItems.get(0);
            } else {
                //把数据保存到缓存
                redisClient.set("ITEM_INFO" + ":" + itemId + ":" + "PARAM", null);
                //设置缓存的有效期
                redisClient.expire("ITEM_INFO" + ":" + itemId + ":" + "PARAM", 30);
            }
            /*释放锁*/
            redisClient.del("SETNX_PARAM_LOCK_KEY");
        }

        return null;
    }

}
