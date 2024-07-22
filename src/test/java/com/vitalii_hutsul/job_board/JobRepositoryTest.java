package com.vitalii_hutsul.job_board;

import com.vitalii_hutsul.job_board.models.Job;
import com.vitalii_hutsul.job_board.repository.JobRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class JobRepositoryTest {
    @Autowired
    private JobRepository jobRepository;

    @Test
    public void testSaveJob() {
        Job job = new Job();
        job.setSlug("test-job");
        job.setTitle("Test Job Title");
        jobRepository.save(job);

        Job foundJob = jobRepository.findById(job.getId()).orElse(null);
        assertThat(foundJob).isNotNull();
        assertThat(foundJob.getTitle()).isEqualTo("Test Job Title");
    }
}
