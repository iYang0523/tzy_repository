package com.jiyun.Dao;

import com.jiyun.pojo.Address;
import com.jiyun.pojo.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface UserDao extends JpaRepository<User,Long> {

    @Query(nativeQuery = true,value = "select * from user where (?1 is null or name like %?1%) "
            + " and (?2 is null or birthday = ?2) and (?3 is null or dizhi like %?3%) order by birthday desc",
            countQuery = "select count(*) from user where (?1 is null or name like %?1%) "
                    + " and (?2 is null or birthday = ?2) and (?3 is null or dizhi like %?3%)")
    Page<User> findall(String name, Date birthday,String dizhi,Pageable pageList);

@Query(nativeQuery = true,value = "select count(id) from user where sex =?1 group by sex")
    Integer findsex(String n);

}
// + " and (?2 is null or dizhi like %?2%) "
//         + " and (?2 is null or dizhi like %?2%) "