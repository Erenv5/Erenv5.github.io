package com.hotel.hotel.service.room;

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
    List<Room> getAllRooms();


    /**
     * 获取所有空房
     * @param status
     * @return
     */
    List<Room> getRoomsByStatus(String status);

    /**
     * 通过 ID 查找房间
     * @param id
     * @return
     */
    Room getRoomById(String id);

    /**
     * 修改房间状态信息
     * @param roomId
     * @param status
     * @return
     */
    Room changeStatus(String roomId,String status);

    /**
     * 保存或更新房间信息
     * @param room
     * @return
     */
    Room saveOrUpdateRoom(Room room);

    /**
     * ID 对应 room 是否存在
     * @param id
     * @return
     */
    boolean ifRoomEmpty(String id);


    boolean ifRoomHourly(String id);

    boolean ifRoomExists(String id);
}
