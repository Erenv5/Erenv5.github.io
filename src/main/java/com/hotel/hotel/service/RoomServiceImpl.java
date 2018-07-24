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

    @Transactional
    @Override
    public Page<Room> getRoomsByStatusAndType(String type, String status, Pageable pageable){
        Page<Room> rooms = roomRespository.findByTypeAndStatus(type, status, pageable);
        return rooms;
    }

    @Transactional
    @Override
    public List<Room> getAllRooms() {
        List<Room> rooms = roomRespository.findAll();
        for(Room room : roomRespository.findAll()){
            rooms.add(room);
        }
        return rooms;
    }


}
