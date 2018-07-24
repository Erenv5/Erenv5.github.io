package com.hotel.hotel.repository;

import com.hotel.hotel.domain.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRespository extends JpaRepository<Room, String> {

    Page<Room> findByTypeAndStatus(String type, String status, Pageable pageable);
}
