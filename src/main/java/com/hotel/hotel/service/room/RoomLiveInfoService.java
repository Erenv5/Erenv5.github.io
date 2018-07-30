package com.hotel.hotel.service.room;


import com.hotel.hotel.domain.RoomLiveInfo;

public interface RoomLiveInfoService {
    //保存入住信息
    RoomLiveInfo save(RoomLiveInfo roomLiveInfo);

    //手机号有没有对应入住信息
    boolean telNoLived(String tel);

    //手机号对应入住信息
    RoomLiveInfo getByTel(String tel);

    //根据 ID 查询入住信息
    RoomLiveInfo getById(Long id);

    //根据 ID 删除入住信息
    RoomLiveInfo delete(Long id);
}
