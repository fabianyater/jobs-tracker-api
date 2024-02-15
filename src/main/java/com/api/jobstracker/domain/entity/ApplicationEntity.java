package com.api.jobstracker.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

import java.time.ZonedDateTime;

@Getter
@Setter
@ToString
@Entity(name = "applications")
@Table(name = "applications")
public class ApplicationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NaturalId
    @Column(name = "application_id", nullable = false, length = 20, unique = true)
    private String applicationId;

    @Column(name = "application_date")
    private ZonedDateTime applicationDate;

    @Column(name = "url", columnDefinition = "text")
    private String url;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @ManyToOne
    @JoinColumn(name = "job_entity_id")
    private JobEntity jobEntity;

    @ManyToOne
    @JoinColumn(name = "company_entity_id")
    private CompanyEntity companyEntity;
}
