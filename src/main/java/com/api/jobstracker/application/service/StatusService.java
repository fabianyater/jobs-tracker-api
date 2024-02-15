package com.api.jobstracker.application.service;

import com.api.jobstracker.infrastructure.model.response.StatusResponse;

import java.util.List;

public interface StatusService {
    List<StatusResponse> getAllStatuses();

}
