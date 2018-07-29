package com.hotel.hotel.repository;

import com.hotel.hotel.domain.Clerk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClerkRepository extends JpaRepository<Clerk, Long> {

    Clerk findByUsername(String username);

}
