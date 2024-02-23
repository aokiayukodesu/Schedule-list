package com.example.schedule.demo.controller;

import com.example.schedule.demo.Exception.UserNotFoundException;
import com.example.schedule.demo.entity.Schedule;
import com.example.schedule.demo.form.CreateForm;
import com.example.schedule.demo.form.UpdateForm;
import com.example.schedule.demo.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.ZonedDateTime;
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
    public Schedule findById(@PathVariable Integer id) {
        return scheduleService.findById(id);
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(
            UserNotFoundException e, HttpServletRequest request) {
        Map<String, String> body = Map.of(
                "timestamp", ZonedDateTime.now().toString(),
                "status", String.valueOf(HttpStatus.NOT_FOUND.value()),
                "error", HttpStatus.NOT_FOUND.getReasonPhrase(),
                "message", e.getMessage(),
                "path", request.getRequestURI());
        return new ResponseEntity(body, HttpStatus.NOT_FOUND);
    }


    @PostMapping("/schedules")
    public ResponseEntity<Map<String, String>> createTable(@RequestBody @Validated CreateForm form, UriComponentsBuilder uriBuilder) {
        Schedule schedule = scheduleService.createTable(form.getTitle(), form.getScheduleDate(), form.getScheduleTime());
        URI location = UriComponentsBuilder.fromUriString("http://localhost:8080").path("/schedules/{id}").buildAndExpand(schedule.getId()).toUri();
        Map<String, String> body = Map.of("massage", "your date successfully created");
        return ResponseEntity.created(location).body(body);
    }

    @PutMapping("schedules/edit/{id}")
    public ResponseEntity<Map<String, String>> update(@PathVariable Integer id, @RequestBody @Validated UpdateForm form) {
        scheduleService.updateList(id, form.toEntity());
        Map<String, String> body = Map.of("massage", "update ok");
        return ResponseEntity.ok(body);
    }
}

