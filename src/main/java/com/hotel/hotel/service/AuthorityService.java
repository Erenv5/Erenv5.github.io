package com.hotel.hotel.service;

import com.hotel.hotel.domain.Authority;

public interface AuthorityService {
    /**
     * 根据 ID 查询 Authority
     * @param Id
     * @return
     */
    Authority getAuthorityById(Long Id);
}
