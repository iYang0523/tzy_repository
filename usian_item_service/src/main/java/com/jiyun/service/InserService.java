package com.jiyun.service;

import com.jiyun.mapper.TbItemCatMapper;
import com.jiyun.mapper.TbItemDescMapper;
import com.jiyun.mapper.TbItemMapper;
import com.jiyun.mapper.TbItemParamItemMapper;
import com.jiyun.pojo.TbItemCat;
import com.jiyun.pojo.TbItemDesc;
import com.jiyun.pojo.TbItemParamItem;
import com.jiyun.utils.IDUtils;
import com.jiyun.vo.ItemSearchVo;
import com.jiyun.vo.ItemVo;
import com.jiyun.vo.ItenUpdateIVo;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.Date;

@Service
public class InserService {
    @Autowired
    private TbItemMapper tbItemMapper;
 @Autowired
 private TbItemDescMapper tbItemDescMapper;
 @Autowired
 private TbItemParamItemMapper tbItemParamItemMapper;
 @Autowired
 private TbItemCatMapper tbItemCatMapper;

//  操作mq的客户端
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Transactional
    public void insertTbItem(ItemVo itemVo) {
  // 将数据新增到item
        long id =IDUtils.genItemId();
        Date now = new Date();
        itemVo.setId(id);
        itemVo.setCreated(now);
        itemVo.setUpdated(now);
        tbItemMapper.insertSelective(itemVo);
        // 将数据新增到desc
        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemId(id);
        tbItemDesc.setItemDesc(itemVo.getDesc());
        tbItemDesc.setCreated(now);
        tbItemDescMapper.insertSelective(tbItemDesc);
        // 将数据新增到item_parm_item
        TbItemParamItem tbItemParamItem = new TbItemParamItem();
        tbItemParamItem.setItemId(id);
        tbItemParamItem.setParamData(itemVo.getItemParams());
        tbItemParamItem.setCreated(now);
        tbItemParamItemMapper.insertSelective(tbItemParamItem);

//      // 新增一条到es，发送一条  商品信息 到 mq  item类型--itemSerchVo
        ItemSearchVo itemSearchVo = new ItemSearchVo();
        itemSearchVo.setId(id);
        itemSearchVo.setItem_title(itemVo.getTitle());
        itemSearchVo.setItem_sell_point(itemVo.getSellPoint());
        itemSearchVo.setItem_desc(itemVo.getDesc());
        itemSearchVo.setItem_image(itemVo.getImage());
        itemSearchVo.setItem_price(Double.valueOf(itemVo.getPrice()+""));
//     // 根据新增商品分类的ID，查询分类的名称
        TbItemCat tbItemCat = tbItemCatMapper.selectByPrimaryKey(itemVo.getCid());
        itemSearchVo.setItem_category_name(tbItemCat.getName());  // 分类的名称
        amqpTemplate.convertAndSend("usian.item","add",itemSearchVo);
    }

    public ItenUpdateIVo preUpdateItem(Long itemId) {
      return  tbItemMapper.queryItemDetailById(itemId);
    }

    public void deleteItemById(Long itemId) {
        tbItemMapper.deleteDetailById(itemId);
    }

    @Transactional
    public void updateTbItem(ItemVo itemVo) {
        // 将数据新增到item
        Date now = new Date();
        itemVo.setUpdated(now);
        tbItemMapper.updateByPrimaryKeySelective(itemVo);
        // 将数据新增到desc
        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemId(itemVo.getId());
        tbItemDesc.setItemDesc(itemVo.getDesc());
        tbItemDesc.setCreated(now);
        tbItemDescMapper.updateByPrimaryKeySelective(tbItemDesc);
        // 将数据新增到item_parm_item
        TbItemParamItem tbItemParamItem = new TbItemParamItem();
        tbItemParamItem.setItemId(itemVo.getId());
        tbItemParamItem.setParamData(itemVo.getItemParams());
        tbItemParamItem.setCreated(now);
        tbItemParamItemMapper.updateByPrimaryKeySelective(tbItemParamItem);
    }
}
