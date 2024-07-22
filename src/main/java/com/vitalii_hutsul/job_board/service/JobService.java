package com.vitalii_hutsul.job_board.service;

import com.vitalii_hutsul.job_board.models.Job;
import com.vitalii_hutsul.job_board.dto.LocationStat;
import com.vitalii_hutsul.job_board.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobService {
    @Autowired
    private JobRepository jobRepository;

    public Page<Job> getAllJobs(Pageable pageable) {
        return jobRepository.findAll(pageable);
    }

    public List<Job> getTop10Jobs(Pageable pageable) {
        return jobRepository.findTop10Jobs(pageable);
    }

    public List<LocationStat> getLocationStats() {
        List<Object[]> results = jobRepository.countJobsByLocation();
        return results.stream()
                .map(result -> new LocationStat((String) result[0], (Long) result[1]))
                .collect(Collectors.toList());
    }

    public Job saveJob(Job job) {
        return jobRepository.save(job);
    }

    public void saveAllJobs(List<Job> jobs) {
        jobRepository.saveAll(jobs);
    }
}
