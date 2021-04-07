package com.jiyun.mapper;

import com.jiyun.vo.ItemSearchVo;

import java.util.List;

public interface SearchItemMapper {
    /*
    *查询es
    **/
    List<ItemSearchVo> getItemList();

    ItemSearchVo getItemById(Long itemId);
}
