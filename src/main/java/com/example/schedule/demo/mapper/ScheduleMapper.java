package com.example.schedule.demo.mapper;

import com.example.schedule.demo.entity.Schedule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface ScheduleMapper {

    @Select("SELECT * FROM schedules")
    public List<Schedule> findAll();

}
