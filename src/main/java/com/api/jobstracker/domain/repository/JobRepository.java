package com.api.jobstracker.domain.repository;

import com.api.jobstracker.domain.entity.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobRepository extends JpaRepository<JobEntity, Integer> {
    Optional<JobEntity> findByName(String name);
}
