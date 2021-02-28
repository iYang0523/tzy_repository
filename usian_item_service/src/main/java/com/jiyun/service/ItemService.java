package com.jiyun.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiyun.mapper.TbItemMapper;
import com.jiyun.pojo.TbItem;
import com.jiyun.pojo.TbItemExample;
import com.jiyun.utils.PageResult;
import com.jiyun.vo.ItenUpdateIVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    @Autowired
    private TbItemMapper tbItemMapper;

    public TbItem selectItemInfo(Long itemId) {
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

}
