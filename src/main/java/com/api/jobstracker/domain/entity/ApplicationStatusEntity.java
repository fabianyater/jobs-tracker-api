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
@Entity(name = "application_updates")
@Table(name = "application_updates")
public class ApplicationStatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_updates_id")
    private Integer id;

    @NaturalId
    @Column(name = "application_updates_natural_id", length = 20, nullable = false, unique = true)
    private String applicationUpdatesId;

    @ManyToOne
    @JoinColumn(name = "application_entity_id", nullable = false)
    private ApplicationEntity applicationEntity;

    @ManyToOne
    @JoinColumn(name = "status_entity_id", nullable = false)
    private StatusEntity statusEntity;

    @Column(name = "update_date")
    private ZonedDateTime updateDate;

}
