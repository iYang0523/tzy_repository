package com.jiyun.interceptor;

import com.jiyun.feigen.SSOSeriviceFeign;
import com.jiyun.pojo.TbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 在结算之前判断用户是否登录
 */
@Component
public class UserLoginInterceptor implements HandlerInterceptor {
    @Autowired
    private SSOSeriviceFeign ssoSeriviceFeign;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
      //1.从request对象中获取token
       String token = request.getParameter("token");
        // 2.没有token，就拦截
        if(token==null || token.equals("")){
            return false;
        }
       //2.有token,解析token,解析到用户信息
        TbUser user = ssoSeriviceFeign.getUserByToken(token);
        //3.  无效    拦截
        if(user==null || user.getId()==null){
            return false;
        }
        //3.如果用户信息有效
        return true;
    }
}