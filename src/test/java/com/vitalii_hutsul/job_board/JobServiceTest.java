package com.vitalii_hutsul.job_board;

import com.vitalii_hutsul.job_board.models.Job;
import com.vitalii_hutsul.job_board.repository.JobRepository;
import com.vitalii_hutsul.job_board.service.JobService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JobServiceTest {

    @Mock
    private JobRepository jobRepository;

    @InjectMocks
    private JobService jobService;

    @Test
    public void testGetAllJobs() {
        Job job = new Job();
        job.setSlug("test-job");
        job.setTitle("Test Job Title");

        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "title"));
        Page<Job> jobPage = new PageImpl<>(Collections.singletonList(job), pageable, 1);

        when(jobRepository.findAll(pageable)).thenReturn(jobPage);

        Page<Job> jobs = jobService.getAllJobs(pageable);
        assertThat(jobs.getContent()).hasSize(1);
        assertThat(jobs.getContent().get(0).getTitle()).isEqualTo("Test Job Title");
    }
}
