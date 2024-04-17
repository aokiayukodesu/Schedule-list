package com.example.schedule.demo.controller;

import com.example.schedule.demo.Exception.ScheduleNotFoundException;
import com.example.schedule.demo.entity.Schedule;
import com.example.schedule.demo.service.ScheduleService;
import com.example.schedule.demo.service.ScheduleServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ScheduleController.class)
@AutoConfigureMockMvc
class ScheduleControllerTest {

    @InjectMocks
    ScheduleController scheduleController;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ScheduleServiceImpl scheduleServiceImpl;


    @Test
    void findAllメソッドで全てのレコードを取得できる() throws Exception {
        List<Schedule> schedule = List.of(new Schedule(1, "予防接種", LocalDate.of(2024, 04, 20), LocalTime.of(14, 00)),
                (new Schedule(2, "おでかけ", LocalDate.of(2024, 05, 10), LocalTime.of(14, 00))),
                (new Schedule(3, "義母襲来", LocalDate.of(2024, 06, 15), LocalTime.of(14, 00))));
        when(scheduleServiceImpl.findAll()).thenReturn(schedule);

        mockMvc.perform(get("/schedules"))
                .andExpect(status().is(200))
                .equals(schedule);

        verify(scheduleServiceImpl).findAll();

    }
}