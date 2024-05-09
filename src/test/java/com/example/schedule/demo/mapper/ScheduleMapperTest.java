package com.example.schedule.demo.mapper;

import com.example.schedule.demo.entity.Schedule;
import com.example.schedule.demo.service.ScheduleService;
import com.example.schedule.demo.service.ScheduleServiceImpl;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DBRider
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ScheduleMapperTest {

    @Autowired
    ScheduleMapper scheduleMapper;


    @Test
    @DataSet(value = "datasets/schedules.yml")
    @Transactional
    void findAllメソッドで全てのデータが取得できること() {
        List<Schedule> schedule = scheduleMapper.findAll();
        assertThat(schedule)
                .hasSize(2)
                .contains(new Schedule(1, "親知らず", LocalDate.of(2024, 05, 15), LocalTime.of(10, 00, 00)),
                        new Schedule(2, "ストレンジャーシングス新シーズン配信", LocalDate.of(2024, 05, 27), LocalTime.of(10, 00, 00))
                );
    }
}
