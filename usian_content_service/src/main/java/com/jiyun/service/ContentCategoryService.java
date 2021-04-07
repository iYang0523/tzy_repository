package com.jiyun.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiyun.mapper.TbContentCategoryMapper;
import com.jiyun.mapper.TbContentMapper;
import com.jiyun.pojo.*;
import com.jiyun.utils.AdNode;
import com.jiyun.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ContentCategoryService {
    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;

    @Autowired
    private TbContentMapper tbContentMapper;

    public List<TbContentCategory> selectContentCategoryByParentId(Long parentId) {

        TbContentCategoryExample tbContentCategoryExample = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = tbContentCategoryExample.createCriteria();
        criteria.andStatusEqualTo(1);
criteria.andParentIdEqualTo(parentId);
List<TbContentCategory> list=tbContentCategoryMapper.selectByExample(tbContentCategoryExample);
        return list;
    }

    public Integer insertContentCategory(TbContentCategory tbContentCategory) {
        tbContentCategory.setCreated(new Date());
        tbContentCategory.setUpdated(new Date());
        tbContentCategory.setStatus(1);
        tbContentCategory.setIsParent(false);
        tbContentCategory.setSortOrder(1);
      Integer contentCate= tbContentCategoryMapper.insert(tbContentCategory);
 // 2. 添加一条消息判断他的父节点是否是父节点
        TbContentCategory contentCategory =tbContentCategoryMapper.selectByPrimaryKey(tbContentCategory.getParentId());
   //2.2 判断当前父节点是否是叶子节点
        if(!contentCategory.getIsParent()){
    contentCategory.setIsParent(true);
    contentCategory.setUpdated(new Date());
    tbContentCategoryMapper.updateByPrimaryKey(contentCategory);
        }
        return contentCate;
    }

    public Integer deleteContentCategoryById(Long categoryId) {
        TbContentCategory tbContentCategory = tbContentCategoryMapper.selectByPrimaryKey(categoryId);
        if(tbContentCategory.getIsParent()==true){
            return 0;
        }
  // 把删除的状态改成2     相当于删除
        TbContentCategory tbContentCategory2 = new TbContentCategory();
         tbContentCategory2.setId(categoryId);
         tbContentCategory2.setStatus(2);
        tbContentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory2);
  // 判断他是否还有子叶
        TbContentCategoryExample tbContentCategoryExample = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = tbContentCategoryExample.createCriteria();
        criteria.andParentIdEqualTo(tbContentCategory.getParentId());
        criteria.andStatusEqualTo(1);
      List<TbContentCategory> list=tbContentCategoryMapper.selectByExample(tbContentCategoryExample);

      if(list.size()==0){
          // 把他设置成子叶
          TbContentCategory tbContentCategory1 = new TbContentCategory();
          tbContentCategory1.setId(tbContentCategory.getParentId());
          tbContentCategory1.setIsParent(false);
          tbContentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory1);
      }
        return 200;
    }

    public void updateContentCategory(Long id, String name) {
        TbContentCategory tbContentCategory = new TbContentCategory();
        tbContentCategory.setId(id);
        tbContentCategory.setName(name);
        tbContentCategory.setUpdated(new Date());
        tbContentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory);
    }

    public PageResult selectTbContentAllByCategoryId(Integer page, Integer rows, Long categoryId) {
        PageHelper.startPage(page,rows);// 分页
        TbContentExample example = new TbContentExample(); //设置各种查询条件的
        example.setOrderByClause("created desc");
        //添加条件
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);

        List<TbContent> tbItems = tbContentMapper.selectByExample(example);
        PageInfo<TbContent> pageInfo = new PageInfo<>(tbItems);  //页的结果集对象 --》 pageHepler的类型

        PageResult<TbContent> pageResult = new PageResult<>(page,pageInfo.getPages(),tbItems);

        return pageResult;
    }

    public void insertTbContent(TbContent tbContent) {
        tbContent.setCreated(new Date());
        tbContent.setUpdated(new Date());
        tbContentMapper.insertSelective(tbContent);
    }

    public void deleteContentByIds(Long id) {
        tbContentMapper.deleteByPrimaryKey(id);
    }


}
