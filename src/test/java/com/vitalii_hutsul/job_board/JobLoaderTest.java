package com.vitalii_hutsul.job_board;

import com.vitalii_hutsul.job_board.dto.JobResponse;
import com.vitalii_hutsul.job_board.models.Job;
import com.vitalii_hutsul.job_board.service.JobLoader;
import com.vitalii_hutsul.job_board.service.JobService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class JobLoaderTest {
    @Mock
    private JobService jobService;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private JobLoader jobLoader;

    @Test
    public void testLoadJobsFromApi() throws Exception {
        JobResponse jobResponse = new JobResponse();
        jobResponse.setData(Collections.singletonList(Job.builder()
                .slug("test-job")
                .company_name("Test company name")
                .title("Test Job Title")
                .description("Test description")
                .remote(false)
                .url("Test url")
                .tags(Collections.singletonList("test tag"))
                .job_types(Collections.singletonList("test job_type"))
                .location("Test Location")
                .build()));


        lenient().when(restTemplate.getForObject(anyString(), any(Class.class))).thenReturn(jobResponse);


        jobLoader.loadJobsFromApi();

        verify(jobService, times(1)).saveAllJobs(any());
    }
}
