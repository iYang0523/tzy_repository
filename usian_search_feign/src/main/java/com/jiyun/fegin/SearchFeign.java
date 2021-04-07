package com.jiyun.fegin;

import com.jiyun.vo.ItemSearchVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("usian-search-service")
public interface SearchFeign {

    @RequestMapping("/frontend/searchItem/importAll")
    boolean importAll();

    @RequestMapping("/frontend/searchItem/list")
    List<ItemSearchVo> selectByq(@RequestParam String q, @RequestParam Integer page,
                                 @RequestParam Integer pageSize);


}
