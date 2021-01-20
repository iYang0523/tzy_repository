package com.jiyun.Service;

import com.jiyun.pojo.Address;
import com.jiyun.pojo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface UserService {
    Page<User> findall(String name,Date birthday, String dizhi ,Pageable pageList);

    List<Address> fff(int oid);

    List<Address> findByAid(Integer oid);

    Address findBySid(Integer shengid);

    void add(User user);

    Integer findsex(String n);

    void del(Long id);

    User upda(Long id);


    void edix(User user);
}
