package com.hotel.hotel.service;

import com.hotel.hotel.domain.Log;
import com.hotel.hotel.repository.LogRepository;

public interface LogService {
    //保存 Log
    Log save(Log log);
}
