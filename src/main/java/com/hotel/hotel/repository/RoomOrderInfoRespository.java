package com.hotel.hotel.repository;

import com.hotel.hotel.domain.RoomOrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomOrderInfoRespository extends JpaRepository<RoomOrderInfo, Long> {

    RoomOrderInfo findByTel(String tel);
}
