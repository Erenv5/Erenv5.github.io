package com.hotel.hotel.repository;

import com.hotel.hotel.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
