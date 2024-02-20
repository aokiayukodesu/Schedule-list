package com.example.schedule.demo.service;

import com.example.schedule.demo.entity.Schedule;
import com.example.schedule.demo.form.CreateForm;
import com.example.schedule.demo.mapper.ScheduleMapper;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleMapper scheduleMapper;

    public ScheduleServiceImpl(ScheduleMapper scheduleMapper) {
        this.scheduleMapper = scheduleMapper;
    }

    @Override
    public List<Schedule> findAll() {
        return scheduleMapper.findAll();
    }

    @Override
    public List<Schedule> findById(Integer id) {
        return scheduleMapper.findById(id);
    }

    @Override
    public void createTable(CreateForm form) {
        scheduleMapper.createTable(form);
    }
}
