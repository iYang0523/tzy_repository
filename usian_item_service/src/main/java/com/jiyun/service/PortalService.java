//package com.jiyun.service;
//
//import com.jiyun.mapper.TbItemCatMapper;
//import com.jiyun.pojo.TbItemCat;
//import com.jiyun.pojo.TbItemCatExample;
//import com.jiyun.utils.CatNode;
//import com.jiyun.utils.CatResult;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class PortalService {
//    @Autowired
//    private TbItemCatMapper tbItemCatMapper;
//
//    public CatResult selectItemCategoryAll() {
//        CatResult catResult = new CatResult();
//        catResult.setData(getCatList(0L));
//        return catResult;
//    }
//
//    private List<?> getCatList(long parentId) {
//        // 查询条件
//        TbItemCatExample tbItemCatExample = new TbItemCatExample();
//        TbItemCatExample.Criteria criteria = tbItemCatExample.createCriteria();
//        criteria.andParentIdEqualTo(parentId);
//        List<TbItemCat> list=tbItemCatMapper.selectByExample(tbItemCatExample);
//
//        List resultList = new ArrayList();
//
//        for(TbItemCat tbItemCat:list){
//            if(tbItemCat.getIsParent()){
//                CatNode catNode = new CatNode();
//                catNode.setName(tbItemCat.getName());
//                catNode.setItem(getCatList(tbItemCat.getParentId()));
//                resultList.add(catNode);
//            }else {
//                resultList.add(tbItemCat.getName());
//            }
//        }
//        return resultList;
//    }
//
//
//}
