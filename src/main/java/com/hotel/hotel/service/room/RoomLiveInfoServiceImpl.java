package com.hotel.hotel.service.room;

import com.hotel.hotel.domain.RoomLiveInfo;
import com.hotel.hotel.domain.RoomOrderInfo;
import com.hotel.hotel.repository.RoomLiveInfoRespository;
import com.hotel.hotel.repository.RoomOrderInfoRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RoomLiveInfoServiceImpl implements RoomLiveInfoService {

    @Autowired
    private RoomLiveInfoRespository roomLiveInfoRespository;

    @Transactional
    @Override
    public RoomLiveInfo save(RoomLiveInfo roomLiveInfo) {
        return roomLiveInfoRespository.save(roomLiveInfo);
    }

    @Override
    public boolean telNoLived(String tel) {
        if(roomLiveInfoRespository.findByTel(tel) != null)
            return false;
        return true;
    }

    @Override
    public RoomLiveInfo getByTel(String tel) {
        return roomLiveInfoRespository.findByTel(tel);
    }

    @Override
    public RoomLiveInfo getById(Long id){
        return roomLiveInfoRespository.getOne(id);
    }

    @Transactional
    @Override
    public RoomLiveInfo delete(Long id) {
        return roomLiveInfoRespository.deleteByInfoId(id);
    }
}
