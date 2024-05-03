package com.example.schedule.demo.service;

import com.example.schedule.demo.Exception.ScheduleNotFoundException;
import com.example.schedule.demo.entity.Schedule;
import com.example.schedule.demo.mapper.ScheduleMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
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
    public Schedule findById(Integer id) throws ScheduleNotFoundException {
        Optional<Schedule> schedule = this.scheduleMapper.findById(id);
        if (schedule.isPresent()) {
            return schedule.get();
        } else {
            throw new ScheduleNotFoundException("入力したidは存在しません");
        }
    }

    @Override
    public Schedule createTable(String title, LocalDate scheduleDate, LocalTime scheduleTime) {
        Schedule schedule = new Schedule(title, scheduleDate, scheduleTime);
        scheduleMapper.createTable(schedule);
        return schedule;
    }

    @Override
    public Schedule updateSchedule(Integer id, Schedule schedule) throws ScheduleNotFoundException {
        Optional<Schedule> existingSchedule = this.scheduleMapper.findById(id);
        if (existingSchedule.isPresent()) {
            scheduleMapper.update(id, schedule);
        } else {
            throw new ScheduleNotFoundException("入力したidは存在しません");
        }
        return schedule;
    }

    @Override
    public void delete(Integer id) {
        Optional<Schedule> existingSchedule = this.scheduleMapper.findById(id);
        if (existingSchedule.isPresent()) {
            scheduleMapper.delete(id);
        } else {
            throw new ScheduleNotFoundException("入力したidは存在しません");
        }
    }
}
