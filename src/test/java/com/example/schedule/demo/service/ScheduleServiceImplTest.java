package com.example.schedule.demo.service;

import com.example.schedule.demo.Exception.ScheduleNotFoundException;
import com.example.schedule.demo.entity.Schedule;
import com.example.schedule.demo.mapper.ScheduleMapper;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ObjectAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.result.StatusResultMatchersExtensionsKt.isEqualTo;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceImplTest {

    @InjectMocks
    ScheduleServiceImpl scheduleServiceImpl;

    @Mock
    ScheduleMapper scheduleMapper;

    @Test
    void findAllメソッドで1件取得できる() {
        when(scheduleMapper.findAll()).thenReturn(List.of(new Schedule(1, "予防接種", LocalDate.of(2024, 11, 10), LocalTime.of(14, 00))));

        List<Schedule> schedule = scheduleServiceImpl.findAll();
        assertEquals(1, schedule.size());
    }

    @Test
    public void 指定したidの情報が取得できる() throws ScheduleNotFoundException {
        doReturn(Optional.of(new Schedule(1, "予防接種", LocalDate.of(2024, 11, 10), LocalTime.of(14, 00)))).when(scheduleMapper).findById(1);

        Schedule actual = scheduleServiceImpl.findById(1);
        assertThat(actual).isEqualTo(new Schedule(1, "予防接種", LocalDate.of(2024, 11, 10), LocalTime.of(14, 00)));
    }


    @Test
    void 指定したidが存在しない場合ScheduleNotFoundExceptionを返す() throws ScheduleNotFoundException {
        when(scheduleMapper.findById(100)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> scheduleServiceImpl.findById(100))
                .isInstanceOfSatisfying(ScheduleNotFoundException.class, (e) -> {
                    assertThat(e.getMessage()).isEqualTo("入力したidは存在しません");
                });
    }
}
