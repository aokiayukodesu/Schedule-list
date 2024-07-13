package com.example.schedule.demo.integrationTest;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;


@SpringBootTest
@AutoConfigureMockMvc
@DBRider
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class IntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DataSet(value = "datasets/schedules.yml")
    @Transactional
    void 予定が全件取得できること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/schedules"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                                [                                
                                {                       
                                    "id":1,
                                        "title":"親知らず",
                                        "scheduleDate":"2024-05-15",
                                        "scheduleTime":"10:00:00"
                                },
                                {
                                    "id":2,
                                      "title":"ストレンジャーシングス新シーズン配信",
                                        "scheduleDate":"2024-05-27",
                                        "scheduleTime":"10:00:00"
                                }
                                ]                             
                                """
                ));
    }

    @Test
    @DataSet(value = "datasets/schedules.yml")
    @Transactional
    void 該当するIDの予定を一件取得できること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/schedules/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                                 {
                                  "id":1,
                                   "title":"親知らず",
                                   "scheduleDate":"2024-05-15",
                                   "scheduleTime":"10:00:00"
                                }                                
                                 """
                ));
    }

    @Test
    @DataSet(value = "datasets/schedules.yml")
    @Transactional
    void 指定したidが存在しない場合404エラーを返すこと() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/schedules/100"))
                .andExpect(MockMvcResultMatchers.status().is(404))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Not Found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("入力したidは存在しません"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/schedules/100"));
    }

    @Test
    @DataSet(value = "datasets/schedules.yml")
    @ExpectedDataSet(value = "datasets/addSchedules.yml", ignoreCols = "id")
    @Transactional
    void 新規で予定と予定日時を入力すると登録されること() throws Exception {
        String requestBody = """
                   {
                   "title": "一時保育",
                   "scheduleDate": "2025-05-21",
                   "scheduleTime": "10:00:00"
                   }                                                               
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/schedules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json("""
                        { 
                        "massage" : "your date successfully created" 
                        } 
                        """));
    }

    @Test
    @DataSet(value = "datasets/schedules.yml")
    @ExpectedDataSet(value = "datasets/updateSchedules.yml")
    @Transactional
    void 該当するIDの予定情報を更新する() throws Exception {
        String requestBody = """
                   {                
                   "title": "親知らず",
                   "scheduleDate": "2024-07-17",
                   "scheduleTime": "13:00:00"
                   }                                                               
                """;
        mockMvc.perform(MockMvcRequestBuilders.put("/schedules/edit/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        { 
                        "massage" : "update ok" 
                        } 
                        """));
    }

    @Test
    @DataSet(value = "datasets/schedules.yml")
    @Transactional
    void updateリクエストで指定したidが存在しない場合404エラーを返すこと() throws Exception {
        String requestBody = """
                {
                "title": "親知らず",
                "scheduleDate": "2024-07-17",
                "scheduleTime": "13:00:00"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.put("/schedules/edit/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().is(404))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Not Found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("入力したidは存在しません"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/schedules/edit/100"));
    }

    @Test
    @DataSet(value = "datasets/schedules.yml")
    @ExpectedDataSet(value = "datasets/deleteSchedules.yml")
    @Transactional
    void 該当するIDの予定情報を削除する() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/schedules/delete/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        { 
                        "massage" : "delete success" 
                        } 
                        """));
    }

    @Test
    @DataSet(value = "datasets/schedules.yml")
    @Transactional
    void deleteリクエストで指定したidが存在しない場合404エラーを返すこと() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/schedules/delete/100"))
                .andExpect(MockMvcResultMatchers.status().is(404))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Not Found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("入力したidは存在しません"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/schedules/delete/100"));
    }
}

