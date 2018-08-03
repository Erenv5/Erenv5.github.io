package com.hotel.hotel.service.clerk;

import com.hotel.hotel.domain.Clerk;

import java.util.List;

public interface ClerkService {

    boolean usernameExists(String username);

    boolean passwordCorrect(String username, String password);

    Clerk getByUsername(String username);

    Clerk getById(Long id);

    List<Clerk> getAll();

    Clerk saveAndUpdate(Clerk clerk);

    void delete(Long id);

}
