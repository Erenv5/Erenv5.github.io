package com.hotel.hotel.service.clerk;

import com.hotel.hotel.domain.Clerk;

public interface ClerkService {

    boolean usernameExists(String username);

    boolean passwordCorrect(String username, String password);

    Clerk getByUsername(String username);

    Clerk getById(Long id);
}
