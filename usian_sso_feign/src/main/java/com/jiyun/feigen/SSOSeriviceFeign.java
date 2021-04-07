package com.jiyun.feigen;

import com.jiyun.pojo.TbUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


@FeignClient("usian-sso-service")
public interface SSOSeriviceFeign {

    @RequestMapping("/frontend/sso/checkUserInfo/{checkValue}/{checkFlag}")
    public Boolean checkUserInfo(@PathVariable("checkValue") String checkValue,@PathVariable("checkFlag") Integer checkFlag);


    @RequestMapping("/frontend/sso/userRegister")
    Integer userRegister(@RequestBody TbUser tbUser);

    @RequestMapping("/frontend/sso/userLogin")
  Map userLogin(@RequestParam String username,@RequestParam String password);

    @RequestMapping("/frontend/sso/getUserByToken/{token}")
    TbUser getUserByToken(@PathVariable String token);

@RequestMapping("/frontend/sso/logOut")
    Boolean logOut(@RequestParam String token);
}
