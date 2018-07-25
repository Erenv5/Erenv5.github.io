package com.hotel.hotel.service;

import com.hotel.hotel.domain.Authority;
import com.hotel.hotel.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityServiceImpl implements  AuthorityService{

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public Authority getAuthorityById(Long id) {
        return authorityRepository.getOne(id);
    }
}


