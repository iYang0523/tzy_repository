package com.jiyun.controller;

import com.jiyun.pojo.TbUser;
import com.jiyun.service.SSOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/frontend/sso")
public class SSOController {
    @Autowired
    private SSOService ssoService;
    /**
     * 对用户的注册信息(用户名与电话号码)做数据校验
     */
    @RequestMapping("/checkUserInfo/{checkValue}/{checkFlag}")
    public boolean checkUserInfo(@PathVariable String checkValue, @PathVariable Integer checkFlag){
        return this.ssoService.checkUserInfo(checkValue, checkFlag);
    }

    @RequestMapping("/userRegister")
    public Integer userRegister(@RequestBody TbUser tbUser)
    {
        return this.ssoService.userRegister(tbUser);
    }

    @RequestMapping("/userLogin")
    Map userLogin(@RequestParam String username, @RequestParam String password){
        return this.ssoService.userLogin(username,password);
    }

    @RequestMapping("/getUserByToken/{token}")
    @ResponseBody
    TbUser getUserByToken(@PathVariable("token") String token){
        return ssoService.getUserByToken(token);
    }


    @RequestMapping("/logOut")
    Boolean logOut(@RequestParam String token){
    return   ssoService.logOut(token);
    }
}
