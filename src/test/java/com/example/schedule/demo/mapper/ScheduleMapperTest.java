package com.example.schedule.demo.mapper;

import com.example.schedule.demo.entity.Schedule;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

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

    @Test
    @DataSet(value = "datasets/schedules.yml")
    @Transactional
    void 存在するidを指定した場合に情報が取得できること() {
        Optional<Schedule> schedule = scheduleMapper.findById(1);
        assertThat(schedule)
                .contains(new Schedule(1, "親知らず", LocalDate.of(2024, 05, 15),
                        LocalTime.of(10, 00, 00)));
    }

    @Test
    @DataSet(value = "datasets/schedules.yml")
    @Transactional
    void 存在しないidを指定した場合に空を返す() {
        Optional<Schedule> schedule = scheduleMapper.findById(100);
        assertThat(schedule).isEmpty();
    }

    @Test
    @DataSet(value = "datasets/schedules.yml")
    @ExpectedDataSet(value = "datasets/addSchedules.yml", ignoreCols = "id")
    @Transactional
    void 予定名と予定日時の情報が登録されること() {
        Schedule schedule = new Schedule("一時保育", LocalDate.of(2024, 05, 21),
                LocalTime.of(10, 00, 00));
        scheduleMapper.create(schedule);
    }

    @Test
    @DataSet(value = "datasets/schedules.yml")
    @ExpectedDataSet(value = "datasets/checkId.yml", ignoreCols = "id")
    @Transactional
    void 予定名と予定日時の情報が登録された場合idが補完されること() {
        Schedule checkId = new Schedule("一時保育", LocalDate.of(2025, 05, 21),
                LocalTime.of(10, 00, 00));
        scheduleMapper.create(checkId);
        Assertions.assertNotNull(checkId);
    }

    @Test
    @DataSet(value = "datasets/schedules.yml")
    @ExpectedDataSet(value = "datasets/updateSchedules.yml")
    @Transactional
    void 指定したidが存在した場合そのidの情報を更新する() {
        Schedule requestedSchedule = new Schedule("親知らず", LocalDate.of(2024, 07, 17),
                LocalTime.of(13, 00, 00));
        scheduleMapper.update(1, requestedSchedule);
    }
}
    
