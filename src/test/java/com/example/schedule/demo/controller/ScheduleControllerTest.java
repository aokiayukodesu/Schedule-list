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
import java.util.Optional;


import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

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

    @Test
    void findByIdで指定されたidが存在した場合に情報を返す() throws Exception {
        Schedule schedule = new Schedule(1, "予防接種", LocalDate.of(2024, 04, 20), LocalTime.of(14, 00));
        when(scheduleServiceImpl.findById(1)).thenReturn(schedule);

        mockMvc.perform(get("/schedules/{id}", 1))
                .andExpect(status().is(200))
                .equals(schedule);

        verify(scheduleServiceImpl).findById(1);
    }

    @Test
    void 指定したidが存在しない場合例外を投げる() throws Exception {
        when(scheduleServiceImpl.findById(100)).thenThrow(new ScheduleNotFoundException("入力したidは存在しません"));

        mockMvc.perform(get("/schedules/{id}", 100))
                .andExpect(status().is(404))
                .equals(new ScheduleNotFoundException("入力したidは存在しません"));

        verify(scheduleServiceImpl).findById(100);
    }
}

