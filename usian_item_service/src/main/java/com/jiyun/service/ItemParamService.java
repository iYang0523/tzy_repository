package com.jiyun.service;

import com.jiyun.mapper.TbItemParamMapper;
import com.jiyun.pojo.TbItemParam;
import com.jiyun.pojo.TbItemParamExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemParamService {
    @Autowired
    private TbItemParamMapper tbItemParamMapper;

    public TbItemParam selectItemParamByItemCatId(Integer itemCatId) {
        TbItemParamExample tbItemParamExample = new TbItemParamExample();
       //设置指定的类目id
        TbItemParamExample.Criteria criteria = tbItemParamExample.createCriteria();
        criteria.andItemCatIdEqualTo(Long.valueOf(itemCatId+""));

        List<TbItemParam> tbItemParams = tbItemParamMapper.selectByExampleWithBLOBs(tbItemParamExample);
        if(tbItemParams!=null && tbItemParams.size()>0){
            return tbItemParams.get(0);
        }
        return null;
    }
}
