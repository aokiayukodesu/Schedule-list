package com.example.schedule.demo.service;

import com.example.schedule.demo.entity.Schedule;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ScheduleService {

    List<Schedule> findAll();
}
