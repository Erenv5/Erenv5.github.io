package com.hotel.hotel.service.room;

import com.hotel.hotel.domain.Room;
import com.hotel.hotel.repository.RoomRespository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Room> getAllRooms() {
        return roomRespository.findAll();
    }

    @Override
    public List<Room> getRoomsByStatus(String status) {
        return roomRespository.findByStatus(status);
    }

    @Override
    public Room getRoomById(String id) {
        return roomRespository.getOne(id);
    }

    @Transactional
    @Override
    public Room changeStatus(String roomId, String status) {
        Room room = roomRespository.getOne(roomId);
        room.setStatus(status);
        return roomRespository.save(room);
    }

    @Transactional
    @Override
    public Room saveOrUpdateRoom(Room room) {
        return roomRespository.save(room);
    }

    @Override
    public boolean ifRoomEmpty(String id) {
        if(getRoomById(id).getStatus().compareTo("empty") == 0)
            return true;
        return false;
    }


}
