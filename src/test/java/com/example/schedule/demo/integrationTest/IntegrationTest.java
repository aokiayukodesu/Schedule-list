package com.example.schedule.demo.integrationTest;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;


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
}

