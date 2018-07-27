package com.hotel.hotel.repository;

import com.hotel.hotel.domain.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRespository extends JpaRepository<Room, String> {

    List<Room> findByTypeAndStatus(String type, String status);

    List<Room> findByStatus(String status);
}
