package com.vitalii_hutsul.job_board.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Table(name = "Jobs")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    public String slug;
    public String company_name;
    public String title;
    @Column(columnDefinition = "TEXT")
    public String description;
    public boolean remote;
    public String url;

    @ElementCollection
    @CollectionTable(name = "job_tags", joinColumns = @JoinColumn(name = "job_id"))
    @Column(name = "tag")
    public List<String> tags;

    @ElementCollection
    @CollectionTable(name = "job_types", joinColumns = @JoinColumn(name = "job_id"))
    @Column(name = "job_type")
    public List<String> job_types;

    public String location;
    public int created_at;



}

