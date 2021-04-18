package com.jiyun.Dao;

import com.jiyun.pojo.Address;
import com.jiyun.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressDao extends JpaRepository<Address,Long> {
    @Query(nativeQuery = true,value = "select * from address  where oid = ?1")
    List<Address> fff(int oid);
    @Query(nativeQuery = true,value = "select * from address  where oid=?1")
    List<Address> findByAid(Integer oid);
@Query(nativeQuery = true,value ="select * from address where tid=?1")
    Address findBySid(Integer shengid);
}
