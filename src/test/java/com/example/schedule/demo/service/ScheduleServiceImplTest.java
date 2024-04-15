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
import static org.mockito.Mockito.doNothing;
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
    void findAllメソッドで全てのレコードを取得できる() {
        when(scheduleMapper.findAll()).thenReturn(List.of(new Schedule(1, "予防接種", LocalDate.of(2024, 04, 20), LocalTime.of(14, 00)),
                (new Schedule(2, "おでかけ", LocalDate.of(2024, 05, 10), LocalTime.of(14, 00))),
                (new Schedule(3, "義母襲来", LocalDate.of(2024, 06, 15), LocalTime.of(14, 00)))));

        List<Schedule> schedule = scheduleServiceImpl.findAll();
        assertThat(schedule)
                .hasSize(3)
                .contains(
                        new Schedule(1, "予防接種", LocalDate.of(2024, 04, 20), LocalTime.of(14, 00)),
                        new Schedule(2, "おでかけ", LocalDate.of(2024, 05, 10), LocalTime.of(14, 00)),
                        new Schedule(3, "義母襲来", LocalDate.of(2024, 06, 15), LocalTime.of(14, 00))
                );
    }

    @Test
    public void 指定したidの情報が存在した場合に取得できる() throws ScheduleNotFoundException {
        doReturn(Optional.of(new Schedule(1, "予防接種", LocalDate.of(2024, 11, 15), LocalTime.of(14, 00)))).when(scheduleMapper).findById(1);

        Schedule actual = scheduleServiceImpl.findById(1);
        assertThat(actual).isEqualTo(new Schedule(1, "予防接種", LocalDate.of(2024, 11, 15), LocalTime.of(14, 00)));
    }


    @Test
    void 指定したidが存在しない場合ScheduleNotFoundExceptionを返す() throws ScheduleNotFoundException {
        when(scheduleMapper.findById(100)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> scheduleServiceImpl.findById(100))
                .isInstanceOfSatisfying(ScheduleNotFoundException.class, (e) -> {
                    assertThat(e.getMessage()).isEqualTo("入力したidは存在しません");
                });
    }

    @Test
    void scheduleに渡した値が登録されていること() {
        Schedule schedule = new Schedule("ストレンジャーシングス配信日", LocalDate.of(2024, 05, 27), LocalTime.of(12, 00));
        doNothing().when(scheduleMapper).createTable(schedule);

        Schedule createSchedule = scheduleServiceImpl.createTable("ストレンジャーシングス配信日", LocalDate.of(2024, 05, 27), LocalTime.of(12, 00));
        assertThat(createSchedule).isEqualTo(schedule);
    }

    @Test
    void 指定したidが存在した場合そのidの情報を変更する() throws ScheduleNotFoundException {
        Schedule requestedSchedule = new Schedule("一泊母子旅行", LocalDate.of(2024, 04, 23), LocalTime.of(12, 00));
        doReturn(Optional.of(new Schedule(1, "予防接種", LocalDate.of(2024, 11, 15), LocalTime.of(14, 00)))).when(scheduleMapper).findById(1);
        doNothing().when(scheduleMapper).update(1, requestedSchedule);

        Schedule actual = scheduleServiceImpl.updateSchedule(1, new Schedule("一泊母子旅行", LocalDate.of(2024, 04, 23), LocalTime.of(12, 00)));
        assertThat(actual).isEqualTo(requestedSchedule);
    }

    @Test
    void 指定したidが存在した場合そのidと情報を削除する() throws ScheduleNotFoundException {
        Schedule existingSchedule = new Schedule(1, "予防接種", LocalDate.of(2024, 11, 15), LocalTime.of(14, 00));
        doReturn(Optional.of(existingSchedule)).when(scheduleMapper).findById(1);
        doNothing().when(scheduleMapper).delete(1, existingSchedule);

        scheduleServiceImpl.delete(1, existingSchedule);
        verify(scheduleMapper, times(1)).delete(1, existingSchedule);
    }

}

