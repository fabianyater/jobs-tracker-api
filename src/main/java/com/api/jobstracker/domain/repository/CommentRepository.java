package com.api.jobstracker.domain.repository;

import com.api.jobstracker.domain.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
    Optional<CommentEntity> findByApplicationEntity_Id(Integer id);
}
