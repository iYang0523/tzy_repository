package com.jiyun.mapper;

import com.jiyun.pojo.TbItem;
import com.jiyun.pojo.TbItemExample;
import com.jiyun.vo.ItemVo;
import com.jiyun.vo.ItenUpdateIVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbItemMapper {
    int countByExample(TbItemExample example);

    int deleteByExample(TbItemExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbItem record);

    int insertSelective(TbItem record);

    List<TbItem> selectByExample(TbItemExample example);

    TbItem selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbItem record, @Param("example") TbItemExample example);

    int updateByExample(@Param("record") TbItem record, @Param("example") TbItemExample example);

    int updateByPrimaryKeySelective(TbItem record);

    int updateByPrimaryKey(TbItem record);
     // 查询指定商品ID对应的详情信息
   ItenUpdateIVo queryItemDetailById(Long id);

    void deleteDetailById(Long itemId);

}