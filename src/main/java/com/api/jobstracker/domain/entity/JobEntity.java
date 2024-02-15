package com.api.jobstracker.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

@Getter
@Setter
@ToString
@Entity(name = "jobs")
@Table(name = "jobs")
public class JobEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NaturalId
    @Column(name = "job_id", nullable = false, length = 20, unique = true)
    private String jobId;

    @Column(name = "name", nullable = false)
    private String name;
}
