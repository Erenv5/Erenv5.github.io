package com.hotel.hotel.service;

import com.hotel.hotel.domain.Room;
import com.hotel.hotel.repository.RoomRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRespository roomRespository;

    @Override
    public List<Room> getRoomsByStatusAndType(String status, String type){
        return roomRespository.findByTypeAndStatus(type, status);
    }

    @Override
    public List<Room> getAllRooms(Pageable pageable) {
        return roomRespository.findAll();
    }

    @Override
    public List<Room> getRoomsByStatus(String status) {
        return roomRespository.findByStatus(status);
    }


}
