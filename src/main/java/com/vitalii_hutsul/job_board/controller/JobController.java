package com.vitalii_hutsul.job_board.controller;

import com.vitalii_hutsul.job_board.models.Job;
import com.vitalii_hutsul.job_board.dto.LocationStat;
import com.vitalii_hutsul.job_board.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
    @Autowired
    private JobService jobService;

    @GetMapping
    public Page<Job> getAllJobs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable paging = PageRequest.of(page, size, Sort.by(sortBy));
        return jobService.getAllJobs(paging);

    }

    @GetMapping("/top")
    public List<Job> getTop10Jobs() {
        Pageable topTen = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id"));
        return jobService.getTop10Jobs(topTen);
    }

    @GetMapping("/location-stats")
    public List<LocationStat> getLocationStats() {
        return jobService.getLocationStats();
    }
}
