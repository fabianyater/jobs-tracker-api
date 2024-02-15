package com.api.jobstracker.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

@Getter
@Setter
@ToString
@Entity(name = "companies")
@Table(name = "companies")
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NaturalId
    @Column(name = "company_id", nullable = false, length = 20, unique = true)
    private String companyId;

    @Column(name = "name", nullable = false)
    private String name;
}
