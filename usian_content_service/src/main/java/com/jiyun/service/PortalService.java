package com.jiyun.service;

import com.jiyun.RedisClient.RedisClient;
import com.jiyun.mapper.TbItemCatMapper;
import com.jiyun.pojo.TbItemCat;
import com.jiyun.pojo.TbItemCatExample;
import com.jiyun.utils.CatNode;
import com.jiyun.utils.CatResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PortalService {
    @Autowired
    private TbItemCatMapper tbItemCatMapper;
    @Autowired
    private RedisClient redisClient;

    public CatResult selectItemCategoryAll() {
        //查询缓存
        CatResult catResultRedis = (CatResult)redisClient.get("PROTAL_CATRESULT_KEY");  //id
        if(catResultRedis!=null){
            return catResultRedis;
        }

        CatResult catResult = new CatResult();
        catResult.setData(getCatList(0L));

        redisClient.set("PROTAL_CATRESULT_KEY",catResult);
        return catResult;
    }

    private List<?> getCatList(long parentId) {
        // 查询条件
        TbItemCatExample tbItemCatExample = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = tbItemCatExample.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> list=tbItemCatMapper.selectByExample(tbItemCatExample);
 // 根据条件查的 然后new 一个集合
        List resultList = new ArrayList();
       // 遍历这个list去判断isparent是否等于父节点 是父节点就把集合添加到CatNode里
        for(TbItemCat tbItemCat:list){
            if(tbItemCat.getIsParent()){
                CatNode catNode = new CatNode();
                catNode.setName(tbItemCat.getName());
                catNode.setItem(getCatList(tbItemCat.getId()));
                resultList.add(catNode);
            }else {
                resultList.add(tbItemCat.getName());
            }
        }
        return resultList;
    }
}
