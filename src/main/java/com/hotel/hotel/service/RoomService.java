package com.hotel.hotel.service;

import com.hotel.hotel.domain.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoomService {

    /**
     * 根据提供的状态和类型查询房间列表
     * @param status
     * @param type
     * @return
     */
    List<Room> getRoomsByStatusAndType(String status, String type);


    /**
     * 获取所有房间信息
     * @return
     */
    List<Room> getAllRooms(Pageable pageable);


    /**
     * 获取所有空房
     * @param status
     * @return
     */
    List<Room> getRoomsByStatus(String status);



}
