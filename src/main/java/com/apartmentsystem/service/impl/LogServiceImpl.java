package com.apartmentsystem.service.impl;

import com.apartmentsystem.mapper.LogMapper;
import com.apartmentsystem.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LogMapper logMapper;

    @Override
    public void insertLog(int user_id, String action) {
        logMapper.insertLog(user_id, action);
    }
}
