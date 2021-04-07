package com.jiyun.controller;

import com.jiyun.Service.SearchItemService;
import com.jiyun.vo.ItemSearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/frontend/searchItem")
public class ItemSearchController {
@Autowired
private SearchItemService searchItemService;

@RequestMapping("/importAll")
  public boolean importAll() {
  return  searchItemService.importAll();
}

@RequestMapping("/list")
List<ItemSearchVo> selectByq(@RequestParam String q, @RequestParam Integer page,
                             @RequestParam Integer pageSize) throws IOException {
        return searchItemService.selectByq(q,page,pageSize);

}
}
