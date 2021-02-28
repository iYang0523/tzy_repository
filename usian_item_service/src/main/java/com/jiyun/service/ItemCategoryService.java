package com.jiyun.service;

import com.jiyun.mapper.TbItemCatMapper;
import com.jiyun.pojo.TbItemCat;
import com.jiyun.pojo.TbItemCatExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemCategoryService {
    @Autowired
    private TbItemCatMapper  tbItemCatMapper;

    public List<TbItemCat> selectItemCategoryByParentId(Integer id) {

         // 封装查询条件
        TbItemCatExample tbItemCatExample = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = tbItemCatExample.createCriteria();
        //设置类目的父ID
        criteria.andParentIdEqualTo(Long.valueOf(id+""));
        List<TbItemCat> tbItemCats = tbItemCatMapper.selectByExample(tbItemCatExample);
        return tbItemCats;
    }
}
