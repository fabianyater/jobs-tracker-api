package com.api.jobstracker.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

@Getter
@Setter
@ToString
@Entity(name = "status")
@Table(name = "status")
public class StatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NaturalId
    @Column(name = "status_id", nullable = false, length = 20, unique = true)
    private String statusId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "color")
    private String color;

}
