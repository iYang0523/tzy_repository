package com.jiyun.Impl;

import com.jiyun.Dao.AddressDao;
import com.jiyun.Dao.UserDao;
import com.jiyun.Service.UserService;
import com.jiyun.pojo.Address;
import com.jiyun.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class UserImpl implements UserService {
    @Autowired
    UserDao userDao;
    @Autowired
    AddressDao addressDao;
    @Override
    public Page<User> findall(String name,Date birthday, String dizhi, Pageable pageList) {
        System.out.println(birthday);
        return userDao.findall(name,birthday,dizhi,pageList);
    }

    @Override
    public List<Address> fff(int oid) {
        return addressDao.fff(oid);
    }

    @Override
    public List<Address> findByAid(Integer oid) {
        return addressDao.findByAid(oid);
    }

    @Override
    public Address findBySid(Integer shengid) {
        return addressDao.findBySid(shengid);
    }

    @Override
    public void add(User user) {
        userDao.save(user);
    }

    @Override
    public Integer findsex(String n) {
        return userDao.findsex(n);
    }

    @Override
    public void del(Long id) {
        userDao.deleteById(id);
    }

    @Override
    public User upda(Long id) {
        return userDao.findById(id).get();
    }

    @Override
    public void edix(User user) {
        userDao.save(user);
    }


}
