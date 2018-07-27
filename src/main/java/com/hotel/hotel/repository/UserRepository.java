package com.hotel.hotel.repository;

import com.hotel.hotel.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 根据用户的 姓名 查询用户信息
     * @param name
     * @param pageable
     * @return
     */
    List<User> findByNameLike(String name);

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 根据电话查询用户
     * @param telephone
     * @return
     */
    User findByTelephone(String telephone);

}
