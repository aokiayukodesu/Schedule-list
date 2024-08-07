package com.example.schedule.demo.form;

import com.example.schedule.demo.entity.Schedule;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class UpdateForm {
    private Integer id;

    @NotBlank(message = "文字を入力してください")
    @Size(max = 100)
    private String title;


    @Future(message = "過去の日付は入力できません")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate scheduleDate;


    @DateTimeFormat(pattern = "hh:mm:ss")
    private LocalTime scheduleTime;

    public Schedule toEntity() {
        Schedule schedule = new Schedule(title, scheduleDate, scheduleTime);
        schedule.setTitle(title);
        schedule.setScheduleDate(scheduleDate);
        schedule.setScheduleTime(scheduleTime);
        return schedule;
    }


    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(LocalDate scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public LocalTime getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(LocalTime scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    @AssertTrue(message = "scheduleDateとscheduleTimeは必須です")
    public boolean isScheduleDateOrScheduleTimeNotBlank() {
        if (scheduleDate == null || scheduleTime == null) {
            return false;
        } else {
            return true;
        }
    }
}

