package com.jiyun.service;

import com.jiyun.RedisClient.RedisClient;
import com.jiyun.mapper.TbUserMapper;
import com.jiyun.pojo.TbUser;
import com.jiyun.pojo.TbUserExample;
import com.jiyun.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class SSOService {
    @Autowired
    private TbUserMapper tbUserMapper;
    
    @Autowired
    private RedisClient redisClient;

    public boolean checkUserInfo(String checkValue, Integer checkFlag) {
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        //1.查询条件根据参数动态生成，1,2分别代表username，phone
        if(checkFlag == 1){
            criteria.andUsernameEqualTo(checkValue);
        }else if(checkFlag ==2){
            criteria.andPhoneEqualTo(checkValue);
        }

        //2.从tb_user表中查询数据
        List<TbUser> list = tbUserMapper.selectByExample(example);
        //3.判断查询结果，如果查询到数据返回false
        if(list ==null || list.size()==0){
            return true;
        }
        return false;
    }

    public Integer userRegister(TbUser tbUser) {
        String pwd = MD5Utils.digest(tbUser.getPassword());
        tbUser.setPassword(pwd);
        // 补齐数据
        tbUser.setCreated(new Date());
        tbUser.setUpdated(new Date());
        return tbUserMapper.insert(tbUser);
    }

    public Map userLogin(String username, String password) {
        //1.判断用户名和密码是否正确
        String pwd = MD5Utils.digest(password);
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        criteria.andPasswordEqualTo(pwd);
        List<TbUser> userList = tbUserMapper.selectByExample(example);
          if(userList==null || userList.size()<=0){
              return  null;
          }
          //2.登录
        TbUser tbUser = userList.get(0);
        // 2、登录成功后生成token。Token相当于原来的jsessionid，字符串，可以使用uuid。
        String token = UUID.randomUUID().toString();
        // 3、把用户信息保存到redis。Key就是token，value就是TbUser对象转换成json。
        tbUser.setPassword(null);
        redisClient.set("USER_INFO" + ":" + token, tbUser);
        // 5、设置key的过期时间。模拟Session的过期时间。
        redisClient.expire("USER_INFO" + ":" + token, 10*60);

        Map<String,String> map = new HashMap<String,String>();
        map.put("token",token);
        map.put("userid",tbUser.getId().toString());
        map.put("username",tbUser.getUsername());
        return map;
    }

    public TbUser getUserByToken(String token) {
        TbUser tbUser = (TbUser) redisClient.get("USER_INFO" + ":" + token);
        if(tbUser!=null){
            //需要重置key的过期时间。
            redisClient.expire("USER_INFO"+":"+token,10*60);
            return tbUser;
        }
        return null;
    }

    public Boolean logOut(String token) {
        return  redisClient.del("USER_INFO"+":"+token);
    }
}
