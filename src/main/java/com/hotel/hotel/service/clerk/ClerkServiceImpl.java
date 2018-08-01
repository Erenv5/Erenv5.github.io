package com.hotel.hotel.service.clerk;

import com.hotel.hotel.domain.Clerk;
import com.hotel.hotel.repository.ClerkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClerkServiceImpl implements ClerkService {

    @Autowired
    private ClerkRepository clerkRepository;

    @Override
    public boolean usernameExists(String username) {
        if(clerkRepository.findByUsername(username) != null)
            return true;
        return false;
    }

    @Override
    public boolean passwordCorrect(String username, String password) {
        if(clerkRepository.findByUsername(username).getPassword().compareTo(password) == 0)
            return true;
        return false;
    }

    @Override
    public Clerk getByUsername(String username) {
        return clerkRepository.findByUsername(username);
    }

    @Override
    public Clerk getById(Long id) {
        return clerkRepository.getOne(id);
    }
}
