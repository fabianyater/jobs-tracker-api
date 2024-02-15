package com.api.jobstracker.domain.repository;

import com.api.jobstracker.domain.entity.ApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Integer> {
    Optional<ApplicationEntity> findByCompanyEntity_Id(Integer id);
    Optional<ApplicationEntity> findByJobEntity_Id(Integer id);

    @Query(value = "SELECT s.name, au.update_date, s.color " +
            "FROM application_updates au " +
            "INNER JOIN status s ON au.status_entity_id = s.id " +
            "WHERE au.application_entity_id = :applicationId", nativeQuery = true)
    List<Object[]> findStatusesByApplicationId(@Param("applicationId") Integer applicationId);



}
