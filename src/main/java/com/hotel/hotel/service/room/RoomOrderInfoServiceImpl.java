package com.hotel.hotel.service.room;

import com.hotel.hotel.domain.RoomOrderInfo;
import com.hotel.hotel.repository.RoomOrderInfoRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
        Optional<RoomOrderInfo> roomOrderInfoOptional = roomOrderInfoRespository.findById(id);
        return roomOrderInfoOptional.get();
    }

    @Override
    public RoomOrderInfo getInfoByTel(String tel) {
        return roomOrderInfoRespository.findByTel(tel);
    }

    @Transactional
    @Override
    public RoomOrderInfo save(RoomOrderInfo roomOrderInfo) {
        return roomOrderInfoRespository.save(roomOrderInfo);
    }

    @Transactional
    @Override
    public RoomOrderInfo delete(Long id) {
        return roomOrderInfoRespository.deleteByInfoId(id);
    }


}
