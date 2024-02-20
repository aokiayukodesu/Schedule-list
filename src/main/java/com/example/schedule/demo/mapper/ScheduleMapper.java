package com.example.schedule.demo.mapper;

import com.example.schedule.demo.entity.Schedule;
import com.example.schedule.demo.form.CreateForm;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface ScheduleMapper {

    @Select("SELECT * FROM schedules")
    public List<Schedule> findAll();

    @Select("SELECT * FROM schedules WHERE id = #{id}")
    public List<Schedule> findById(Integer id);

    @Insert("insert into schedules(title,scheduleDate,scheduleTime) values (#{title},#{scheduleDate},#{scheduleTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public void createTable(CreateForm form);
}
