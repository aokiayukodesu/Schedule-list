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
    public Schedule findById(Integer id) {
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
    public void updateList(Integer id, Schedule schedule) {
        Optional<Schedule> scheduleId = this.scheduleMapper.findById(id);
        if (scheduleId.isPresent()) {
            scheduleMapper.update(id, schedule);
        } else {
            throw new ScheduleNotFoundException("入力したidは存在しません");
        }
    }


    @Override
    public void delete(Integer id, Schedule schedule) {
        Optional<Schedule> scheduleId = this.scheduleMapper.findById(id);
        if (scheduleId.isPresent()) {
            scheduleMapper.delete(id, schedule);
        } else {
            throw new ScheduleNotFoundException("入力したidは存在しません");
        }
    }
}
