package com.vitalii_hutsul.job_board.dto;

import com.vitalii_hutsul.job_board.models.Job;
import lombok.Data;

import java.util.List;

@Data
public class JobResponse {
    private List<Job> data;
    private Meta meta;
    private Links links;

}
