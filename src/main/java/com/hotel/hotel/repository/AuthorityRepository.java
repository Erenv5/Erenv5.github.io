package com.hotel.hotel.repository;

import com.hotel.hotel.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority,Long> {
}
