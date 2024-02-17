package com.example.schedule.demo.controller;

import com.example.schedule.demo.entity.Schedule;
import com.example.schedule.demo.form.CreateForm;
import com.example.schedule.demo.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.BindException;
import java.net.URI;
import java.util.List;

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

    @PostMapping("/schedules")
    public ResponseEntity<String> createTable(@RequestBody @Validated CreateForm form, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(400).body("再入力してください");
        } else {
            scheduleService.createTable(form);
            URI url = UriComponentsBuilder.fromUriString("http://localhost:8080")
                    .path("/schedules/{id}")
                    .build()
                    .toUri();
            return ResponseEntity.created(url).body("your date successfully created");
        }
    }
}