package com.example.schedule.demo.controller;

import com.example.schedule.demo.Exception.ScheduleNotFoundException;
import com.example.schedule.demo.entity.Schedule;
import com.example.schedule.demo.form.CreateForm;
import com.example.schedule.demo.form.UpdateForm;
import com.example.schedule.demo.mapper.ScheduleMapper;
import com.example.schedule.demo.service.ScheduleService;
import com.example.schedule.demo.service.ScheduleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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

    @Mock
    ScheduleMapper scheduleMapper;


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

    @Test
    void 指定したパスで情報が登録されるか() throws Exception {
        Schedule schedule = new Schedule(1, "予防接種", LocalDate.of(2024, 05, 25), LocalTime.of(14, 00));
        CreateForm form = new CreateForm("予防接種", LocalDate.of(2024, 05, 25), LocalTime.of(14, 00));
        doReturn(schedule).when(scheduleServiceImpl).createTable(form.getTitle(), form.getScheduleDate(), form.getScheduleTime());


        String response = mockMvc.perform(MockMvcRequestBuilders.post("/schedules").contentType(MediaType.APPLICATION_JSON).content(
                        """
                                {
                                "id":1,
                                "title":"予防接種",
                                "scheduleDate":"2024-05-25",
                                "scheduleTime":"14:00:00"
                                }
                                                               
                                """
                ))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        JSONAssert.assertEquals("""
                { 
                "massage" : "your date successfully created" 
                } 
                """, response, JSONCompareMode.STRICT);

        verify(scheduleServiceImpl).createTable(form.getTitle(), form.getScheduleDate(), form.getScheduleTime());

    }

    @Test
    void 指定したidでデータが変更できること() throws Exception {
        Schedule exsintingSchedule = new Schedule(1, "歯医者", LocalDate.of(2024, 06, 25), LocalTime.of(14, 00));
        Schedule updateScheduleTime = new Schedule("歯医者", LocalDate.of(2024, 06, 25), LocalTime.of(16, 00));
        when(scheduleServiceImpl.findById(1)).thenReturn(exsintingSchedule);
        Schedule schedule = new Schedule(1, "歯医者", LocalDate.of(2024, 06, 25), LocalTime.of(16, 00));


        doReturn(schedule).when(scheduleServiceImpl).updateSchedule(1, updateScheduleTime);

        String response = mockMvc.perform(MockMvcRequestBuilders.put("/schedules/edit/{id}", 1).contentType(MediaType.APPLICATION_JSON).content(
                        """
                                {
                                "id":1,
                                "title":"歯医者",
                                "scheduleDate":"2024-06-25",
                                "scheduleTime":"16:00:00"
                                }
                                                               
                                """
                ))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        JSONAssert.assertEquals("""
                { 
                "massage" : "update ok" 
                } 
                """, response, JSONCompareMode.STRICT);

        verify(scheduleServiceImpl).updateSchedule(1, updateScheduleTime);
    }

    @Test
    void updateメソッドで存在しないidを指定した場合にScheduleNotFoundExceptionを投げるか() throws Exception {
        Schedule exsintingSchedule = new Schedule(1, "歯医者", LocalDate.of(2024, 06, 25), LocalTime.of(14, 00));
        Schedule updateScheduleTime = new Schedule("歯医者", LocalDate.of(2024, 06, 25), LocalTime.of(16, 00));
        when(scheduleServiceImpl.updateSchedule(100, updateScheduleTime)).thenThrow(new ScheduleNotFoundException("入力したidは存在しません"));

        mockMvc.perform(MockMvcRequestBuilders.put("/schedules/edit/{id}", 100).contentType(MediaType.APPLICATION_JSON).content(
                        """
                                {
                                "id":100,
                                "title":"歯医者",
                                "scheduleDate":"2024-06-25",
                                "scheduleTime":"16:00:00"
                                }
                                """
                ))
                .andExpect(status().is(404))
                .equals(new ScheduleNotFoundException("入力したidは存在しません"));

        verify(scheduleServiceImpl).updateSchedule(100, updateScheduleTime);
    }
}

