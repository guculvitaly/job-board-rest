package com.vitalii_hutsul.job_board.service;

import com.vitalii_hutsul.job_board.models.Job;
import com.vitalii_hutsul.job_board.dto.JobResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JobLoader implements CommandLineRunner {

    @Autowired
    private JobService jobService;

    @Override
    public void run(String... args) throws Exception {
        loadJobsFromApi();
    }

    @PostConstruct
    public void loadJobsFromApi() {
        String apiUrl = "https://www.arbeitnow.com/api/job-board-api";
        RestTemplate restTemplate = new RestTemplate();

        // Get data from API
        JobResponse response = restTemplate.getForObject(apiUrl, JobResponse.class);
        if (response != null && response!= null) {
            List<Job> jobs = response.getData();
            jobService.saveAllJobs(jobs);
        }
    }
}
