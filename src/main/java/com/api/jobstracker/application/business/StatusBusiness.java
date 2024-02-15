package com.api.jobstracker.application.business;

import com.api.jobstracker.application.service.StatusService;
import com.api.jobstracker.infrastructure.model.response.StatusResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatusBusiness implements StatusService {
    @Override
    public List<StatusResponse> getAllStatuses() {
        return null;
    }
}
