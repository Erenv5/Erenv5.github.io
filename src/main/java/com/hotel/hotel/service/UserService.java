package com.hotel.hotel.service;

import com.hotel.hotel.domain.User;

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

}
