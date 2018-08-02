package com.hotel.hotel.service.log;

import com.hotel.hotel.domain.Log;
import com.hotel.hotel.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    LogRepository logRepository;

    @Transactional
    @Override
    public Log save(Log log) {
        return logRepository.save(log);
    }

    @Override
    public List<Log> getAllLogs() {
        return logRepository.findAll();
    }

    @Override
    public Log getLogById(Long id) {
        return logRepository.getOne(id);
    }
}
