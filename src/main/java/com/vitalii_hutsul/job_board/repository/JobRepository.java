package com.vitalii_hutsul.job_board.repository;

import com.vitalii_hutsul.job_board.models.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    Page<Job> findAll(Pageable pageable);

    @Query("SELECT j FROM Job j ORDER BY j.id DESC")
    List<Job> findTop10Jobs(Pageable pageable);

    @Query("SELECT j.location, COUNT(j) FROM Job j GROUP BY j.location")
    List<Object[]> countJobsByLocation();
}
