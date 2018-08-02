package com.hotel.hotel.service.log;

import com.hotel.hotel.domain.Log;

import java.util.List;

public interface LogService {
    //保存 Log
    Log save(Log log);

    //查看所有 Log
    List<Log> getAllLogs();

    Log getLogById(Long id);
}
