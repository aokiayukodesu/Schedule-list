package com.example.schedule.demo.service;

import com.example.schedule.demo.entity.Schedule;
import com.example.schedule.demo.form.CreateForm;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ScheduleService {

    List<Schedule> findAll();

    List<Schedule> findById(Integer id);

    Schedule createTable(String title, LocalDate scheduleDate, LocalTime scheduleTime);

    void updateList(Integer id, Schedule schedule);

}
