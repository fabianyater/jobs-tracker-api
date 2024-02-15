package com.api.jobstracker.application.business;

import com.api.jobstracker.application.service.ApplicationService;
import com.api.jobstracker.commons.constant.ErrorConstant;
import com.api.jobstracker.commons.constant.FormatConstant;
import com.api.jobstracker.commons.enums.Identifier;
import com.api.jobstracker.commons.utilities.Utilities;
import com.api.jobstracker.domain.entity.*;
import com.api.jobstracker.domain.repository.*;
import com.api.jobstracker.infrastructure.exception.custom.BusinessException;
import com.api.jobstracker.infrastructure.model.request.ApplicationRequest;
import com.api.jobstracker.infrastructure.model.request.StatusRequest;
import com.api.jobstracker.infrastructure.model.response.ApplicationPaginatedResponse;
import com.api.jobstracker.infrastructure.model.response.TimelineResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationBusiness implements ApplicationService {
    public static final int STATUS_SENT_ID = 1;
    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;
    private final ApplicationStatusRepository applicationStatusRepository;
    private final StatusRepository statusRepository;

    @Override
    @Transactional
    public void addApplication(ApplicationRequest applicationRequest) {
        ZonedDateTime applicationDate = ZonedDateTime.now(ZoneId.of(FormatConstant.BOGOTA_ZONE_ID));
        ZonedDateTime updateDate = ZonedDateTime.now(ZoneId.of(FormatConstant.BOGOTA_ZONE_ID));

        try {
            ApplicationEntity application = new ApplicationEntity();

            application.setApplicationId(Utilities.generateRandomId(Identifier.APPLICATION.getCode()));
            application.setJobEntity(saveJob(applicationRequest.getJobName()));
            application.setCompanyEntity(saveCompany(applicationRequest.getCompanyName()));
            application.setUrl(applicationRequest.getUrl());
            application.setApplicationDate(applicationDate);

            applicationRepository.save(application);

            ApplicationStatusEntity applicationStatusEntity = new ApplicationStatusEntity();

            applicationStatusEntity.setApplicationUpdatesId(Utilities.generateRandomId(Identifier.APPLICATION_STATUS.getCode()));
            applicationStatusEntity.setStatusEntity(findSentStatus());
            applicationStatusEntity.setApplicationEntity(application);
            applicationStatusEntity.setUpdateDate(updateDate);

            applicationStatusRepository.save(applicationStatusEntity);

        } catch (Exception e) {
            throw new BusinessException(ErrorConstant.GENERIC_ERROR_CODE, e.getMessage());
        }
    }

    @Override
    public ApplicationPaginatedResponse getAllApplications(int currentPage, int itemsPerPage, Pageable pageable) {
        return null;
    }

    @Override
    public void deleteApplication(Integer applicationId) {

    }

    @Override
    public void updateApplicationStatus(Integer applicationId, StatusRequest statusRequest) {

    }

    @Override
    public List<TimelineResponse> getApplicationTimeline(Integer applicationId) {
        return null;
    }

    private CompanyEntity saveCompany(String name) {
        return companyRepository
                .findByName(name)
                .orElseGet(() -> {
                    CompanyEntity companyEntity = new CompanyEntity();
                    companyEntity.setName(name);
                    companyEntity.setCompanyId(Utilities.generateRandomId(Identifier.COMPANY.getCode()));

                    return companyEntity;
                });
    }

    private JobEntity saveJob(String name) {
        return jobRepository
                .findByName(name)
                .orElseGet(() -> {
                    JobEntity jobEntity = new JobEntity();
                    jobEntity.setName(name);
                    jobEntity.setJobId(Utilities.generateRandomId(Identifier.JOB.getCode()));

                    return jobEntity;
                });
    }

    private StatusEntity findSentStatus() {
        return statusRepository
                .findById(STATUS_SENT_ID)
                .orElseThrow(
                        () -> new BusinessException(ErrorConstant.DATA_NOT_EXIST, ErrorConstant.COMPANY_NOT_FOUND_MESSAGE));
    }
}
