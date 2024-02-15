package com.api.jobstracker.application.service;

import com.api.jobstracker.infrastructure.model.request.ApplicationRequest;
import com.api.jobstracker.infrastructure.model.request.StatusRequest;
import com.api.jobstracker.infrastructure.model.response.ApplicationPaginatedResponse;
import com.api.jobstracker.infrastructure.model.response.TimelineResponse;

import java.awt.print.Pageable;
import java.util.List;

public interface ApplicationService {
    void addApplication(ApplicationRequest applicationRequest);
    ApplicationPaginatedResponse getAllApplications(int currentPage, int itemsPerPage, Pageable pageable);
    void deleteApplication(Integer applicationId);
    void updateApplicationStatus(Integer applicationId, StatusRequest statusRequest);
    List<TimelineResponse> getApplicationTimeline(Integer applicationId);
}
