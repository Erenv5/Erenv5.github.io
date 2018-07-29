package com.hotel.hotel.service.member;

import com.hotel.hotel.domain.User;
import com.hotel.hotel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public User getUserByTel(String tel) {
        return userRepository.findByTelephone(tel);
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
    public List<User> getUserByName(String name) {
        name = "%"+name+"%";
        return userRepository.findByNameLike(name);
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
