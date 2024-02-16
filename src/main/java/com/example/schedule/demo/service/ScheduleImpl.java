package com.example.schedule.demo.service;

import com.example.schedule.demo.entity.Schedule;
import com.example.schedule.demo.mapper.ScheduleMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleImpl implements ScheduleService{

    private final ScheduleMapper scheduleMapper;

    public ScheduleImpl(ScheduleMapper scheduleMapper) {
        this.scheduleMapper = scheduleMapper;
    }

    @Override
    public List<Schedule> findAll() {
        return scheduleMapper.findAll();
    }


}
