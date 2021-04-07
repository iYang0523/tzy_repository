package com.jiyun.controller;

import com.jiyun.feigen.SSOSeriviceFeign;
import com.jiyun.pojo.TbUser;
import com.jiyun.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/frontend/sso")
public class SSOController {
    @Autowired
    private SSOSeriviceFeign ssoSeriviceFeign;

   // 校验
     @RequestMapping("/checkUserInfo/{checkValue}/{checkFlag}")
    public Result checkUserInfo(@PathVariable("checkValue") String checkValue,@PathVariable("checkFlag") Integer checkFlag)
    {
     Boolean checkUserInfo=ssoSeriviceFeign.checkUserInfo(checkValue,checkFlag);
      if(checkUserInfo){
          return Result.ok(checkUserInfo);
      }
      return Result.error("校验失败");
    }
   // 注册
    @RequestMapping("/userRegister")
    public Result userRegister(TbUser tbUser){
      Integer userRegister= ssoSeriviceFeign.userRegister(tbUser);
         if(userRegister==1){
          return Result.ok();
         }
         return Result.error("注册失败");
    }

     // 用户登录
    @RequestMapping("/userLogin")
    public Result userLogin(@RequestParam String username,@RequestParam String password){
      Map map=ssoSeriviceFeign.userLogin(username,password);
       if(map!=null){
           return Result.ok(map);
       }
       return Result.error("登录失败");
    }


    /**
     * 查询用户登录是否过期
     * @param token
     * @return
     */
    @RequestMapping("/getUserByToken/{token}")
    @ResponseBody
    public Result getUserByToken(@PathVariable String token){
     TbUser tbUser=ssoSeriviceFeign.getUserByToken(token);
     if(tbUser!=null){
         return Result.ok();
     }
     return Result.error("登录过期");
 }

   /*
   * 退出登录
   * */
    @RequestMapping("/logOut")
    public Result logOut(@RequestParam String token){
        Boolean logout=ssoSeriviceFeign.logOut(token);
   return Result.ok(logout);
    }
}
