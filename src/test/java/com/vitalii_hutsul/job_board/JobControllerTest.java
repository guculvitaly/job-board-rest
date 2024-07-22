package com.vitalii_hutsul.job_board;


import com.vitalii_hutsul.job_board.controller.JobController;
import com.vitalii_hutsul.job_board.dto.LocationStat;
import com.vitalii_hutsul.job_board.models.Job;
import com.vitalii_hutsul.job_board.service.JobService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(JobController.class)
public class JobControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JobService jobService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllJobs() throws Exception {
        Job job = new Job();
        job.setId(1L);
        job.setSlug("test-job");
        job.setTitle("Test Job Title");

        Pageable pageable = PageRequest.of(0, 1);
        Page<Job> jobPage = new PageImpl<>(Collections.singletonList(job), pageable, 200);


        given(jobService.getAllJobs(pageable)).willReturn(jobPage);


        mockMvc.perform(get("/api/jobs")
                        .param("page", "0")
                        .param("size", "1")
                        .param("sortBy", "id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].slug").value("test-job"))
                .andExpect(jsonPath("$.content[0].title").value("Test Job Title"))
                .andExpect(jsonPath("$.totalElements").value(200))
                .andExpect(jsonPath("$.totalPages").value(200))
                .andExpect(jsonPath("$.number").value(0))
                .andExpect(jsonPath("$.size").value(1));
    }

    @Test
    public void testGetTop10Jobs() throws Exception {

        Job job1 = new Job();
        job1.setId(1L);
        job1.setSlug("job1");
        job1.setTitle("Job Title 1");

        Job job2 = new Job();
        job2.setId(2L);
        job2.setSlug("job2");
        job2.setTitle("Job Title 2");

        List<Job> topJobs = Arrays.asList(job1, job2);


        given(jobService.getTop10Jobs(PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id"))))
                .willReturn(topJobs);


        mockMvc.perform(get("/api/jobs/top")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].slug").value("job1"))
                .andExpect(jsonPath("$[0].title").value("Job Title 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].slug").value("job2"))
                .andExpect(jsonPath("$[1].title").value("Job Title 2"));
    }

    @Test
    public void testGetLocationStats() throws Exception {

        LocationStat locationStat1 = new LocationStat();
        locationStat1.setLocation("Berlin");
        locationStat1.setCount(50L);

        LocationStat locationStat2 = new LocationStat();
        locationStat2.setLocation("Munich");
        locationStat2.setCount(30L);


        given(jobService.getLocationStats()).willReturn(Arrays.asList(locationStat1, locationStat2));


        mockMvc.perform(get("/api/jobs/location-stats")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].location").value("Berlin"))
                .andExpect(jsonPath("$[0].count").value(50))
                .andExpect(jsonPath("$[1].location").value("Munich"))
                .andExpect(jsonPath("$[1].count").value(30));
    }
}
