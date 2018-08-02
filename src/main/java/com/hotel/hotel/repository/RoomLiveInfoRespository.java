package com.hotel.hotel.repository;

import com.hotel.hotel.domain.RoomLiveInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomLiveInfoRespository extends JpaRepository<RoomLiveInfo, Long> {

    RoomLiveInfo findByTel(String tel);

    int deleteByInfoId(Long id);
}
