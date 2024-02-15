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
@Entity(name = "comments")
@Table(name = "comments")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NaturalId
    @Column(name = "comment_id", nullable = false, length = 20, unique = true)
    private String commentId;

    @Column(name = "comment", columnDefinition = "text")
    private String comment;

    @Column(name = "comment_date", nullable = false)
    private ZonedDateTime commentDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "application_entity_id", nullable = false)
    private ApplicationEntity applicationEntity;

}