package com.jiyun.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiyun.mapper.TbItemParamItemMapper;
import com.jiyun.mapper.TbItemParamMapper;
import com.jiyun.pojo.*;
import com.jiyun.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
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

    public PageResult<TbItemParam> selectItemParamAll(Integer page, Integer rows) {
        PageHelper.startPage(page,rows);// 分页
        TbItemParamExample example = new TbItemParamExample(); //设置各种查询条件的

        List<TbItemParam> tbItems = tbItemParamMapper.selectByExampleWithBLOBs(example);
        PageInfo<TbItemParam> pageInfo = new PageInfo<>(tbItems);  //页的结果集对象 --》 pageHepler的类型

        PageResult<TbItemParam> pageResult = new PageResult<>(page,pageInfo.getPages(),tbItems);
        return pageResult;
    }

    public void deleteItemParamById(Long id) {
      tbItemParamMapper.deleteByPrimaryKey(id);
    }


    public Integer insertItemParam(Long itemCatId, String paramData) {
        //1. 判断该类别的商品是否有规格模板
        TbItemParamExample tbItemParamExample = new TbItemParamExample();
        TbItemParamExample.Criteria criteria = tbItemParamExample.createCriteria();

        criteria.andItemCatIdEqualTo(itemCatId);
        List<TbItemParam> itemParamList = tbItemParamMapper.selectByExample(tbItemParamExample);
        if(itemParamList.size()>0)
        {
            return 0;
        }
        // 2.保存规格模板
        Date date = new Date();
        TbItemParam itemParam = new TbItemParam();
        itemParam.setItemCatId(itemCatId);
        itemParam.setCreated(date);
        itemParam.setUpdated(date);
        itemParam.setParamData(paramData);
        return tbItemParamMapper.insertSelective(itemParam);
    }
}
