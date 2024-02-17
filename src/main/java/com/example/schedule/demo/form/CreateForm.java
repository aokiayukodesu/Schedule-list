package com.example.schedule.demo.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;


import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class CreateForm {

   @NotBlank(message = "文字を入力してください")
   @Size(max = 100)
    private String title;

    @NotNull(message = "日付を入力してください")
    @Future(message = "過去の日付は入力できません")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate scheduleDate;


    @NotNull(message = "時間を入力してください")
    @DateTimeFormat(pattern = "hh:mm:ss")
    private LocalTime scheduleTime;

    public CreateForm(String title, LocalDate scheduleDate, LocalTime scheduleTime) {
        this.title = title;
        this.scheduleDate = scheduleDate;
        this.scheduleTime = scheduleTime;
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

}

