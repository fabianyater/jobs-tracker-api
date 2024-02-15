package com.api.jobstracker.domain.repository;

import com.api.jobstracker.domain.entity.ApplicationStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationStatusRepository extends JpaRepository<ApplicationStatusEntity, Integer> {
}
