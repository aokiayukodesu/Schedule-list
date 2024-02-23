package com.example.schedule.demo.service;

import com.example.schedule.demo.Exception.UserNotFoundException;
import com.example.schedule.demo.entity.Schedule;
import com.example.schedule.demo.mapper.ScheduleMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
    public Schedule findById(Integer id) {
        Optional<Schedule> scheduleList = this.scheduleMapper.findById(id);
        if (scheduleList.isPresent()) {
            return scheduleList.get();
        } else {
            throw new UserNotFoundException("入力したidは存在しません");
        }
    }

    @Override
    public Schedule createTable(String title, LocalDate scheduleDate, LocalTime scheduleTime) {
        Schedule schedule = new Schedule(title, scheduleDate, scheduleTime);
        scheduleMapper.createTable(schedule);
        return schedule;
    }

    @Override
    public void updateList(Integer id, Schedule schedule) {
        scheduleMapper.update(id, schedule);
    }

    @Override
    public void delete(Integer id, Schedule schedule) {
        scheduleMapper.delete(id, schedule);
    }
}
