package com.hotel.hotel.service;

import com.hotel.hotel.domain.User;
import com.hotel.hotel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    @Override
    public void registerUser(User user) {
        userRepository.save(user);
    }

    @Override
    public Page<User> getUserByName(String name, Pageable pageable) {
        name = "%"+name+"%";
        return userRepository.findByNameLike(name,pageable);
    }

    @Override
    public boolean usernameExist(String username) {
        User user = userRepository.findByUsername(username);
        if(user == null)
            return false;
        else
            return true;
    }

    @Override
    public boolean telExist(String telephone) {
        User user = userRepository.findByTelephone(telephone);
        if(user == null)
            return false;
        else
            return true;
    }
}
