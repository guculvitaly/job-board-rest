package com.vitalii_hutsul.job_board.dto;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class Meta {
    private int currentPage;
    private int from;
    private String path;
    private int perPage;
    private int to;
    private String terms;
    private String info;
}
