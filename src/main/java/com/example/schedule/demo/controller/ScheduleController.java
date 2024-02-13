package com.example.schedule.demo.controller;

import com.example.schedule.demo.entity.Schedule;
import com.example.schedule.demo.service.ScheduleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Controller
public class ScheduleController {

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    private final ScheduleService scheduleService;

    @GetMapping("/schedule")
    public List<Schedule> findAll(){
        List <Schedule> scheduleList = scheduleService.findAll();
        return scheduleList;
    }

}
