package com.example.schedule.demo.controller;

import com.example.schedule.demo.Response.UserResponse;
import com.example.schedule.demo.entity.Schedule;
import com.example.schedule.demo.form.CreateForm;
import com.example.schedule.demo.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.xml.stream.Location;
import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@Controller
public class ScheduleController {

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    private final ScheduleService scheduleService;

    @GetMapping("/schedules")
    public List<Schedule> findAll() {
        List<Schedule> scheduleList = scheduleService.findAll();
        return scheduleList;
    }

    @GetMapping("schedules/{id}")
    public List<Schedule> findById(@PathVariable Integer id) {
        List<Schedule> scheduleList = scheduleService.findById(id);
        return scheduleList;
    }

    @PostMapping("/schedules")
    public ResponseEntity<Map<String, String>> createTable(@RequestBody @Validated CreateForm form, UriComponentsBuilder uriBuilder) {
        Schedule schedule = scheduleService.createTable(form.getTitle(), form.getScheduleDate(), form.getScheduleTime());
        URI location = UriComponentsBuilder.fromUriString("http://localhost:8080").path("/schedules/{id}").buildAndExpand(schedule.getId()).toUri();
        Map<String, String> body = Map.of("massage", "your date successfully created");
        return ResponseEntity.created(location).body(body);
    }
}

