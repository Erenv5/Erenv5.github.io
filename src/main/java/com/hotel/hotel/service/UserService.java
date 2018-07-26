package com.hotel.hotel.service;

import com.hotel.hotel.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    /**
     * 新建或更新用户信息
     *
     * @param user
     * @return
     */
    User save(User user);

    /**
     * 根据ID删除用户
     * @param id
     * @return
     */
    void delete(Long id);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    User updateUser(User user);

    /**
     * 获取用户信息
     * @param id
     * @return
     */
    User getUserById(Long id);

    User getUserByUsername(String username);

    void registerUser(User user);

    Page<User> getUserByName(String name, Pageable pageable);

    boolean usernameExist(String username);

    boolean telExist(String telephone);
}
