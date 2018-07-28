package com.hotel.hotel.service;

import com.hotel.hotel.domain.RoomOrderInfo;
import com.hotel.hotel.repository.RoomOrderInfoRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomOrderInfoServiceImpl implements RoomOrderInfoService {

    @Autowired
    private RoomOrderInfoRespository roomOrderInfoRespository;

    @Override
    public boolean telNoOrdered(String tel) {
        RoomOrderInfo roomOrderInfo = roomOrderInfoRespository.findByTel(tel);
        if(roomOrderInfo != null)
            return false;
        return true;
    }

    @Override
    public RoomOrderInfo getInfoById(Long id) {
        RoomOrderInfo roomOrderInfo = roomOrderInfoRespository.getOne(id);
        return roomOrderInfo;
    }

    @Override
    public RoomOrderInfo getInfoByTel(String tel) {
        return roomOrderInfoRespository.findByTel(tel);
    }
}
