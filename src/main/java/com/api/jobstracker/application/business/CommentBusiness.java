package com.api.jobstracker.application.business;

import com.api.jobstracker.application.service.CommentService;
import com.api.jobstracker.infrastructure.model.request.CommentRequest;
import com.api.jobstracker.infrastructure.model.response.CommentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentBusiness implements CommentService {
    @Override
    public CommentResponse addComment(CommentRequest commentRequest) {
        return null;
    }

    @Override
    public List<CommentResponse> getAllComments(Integer applicationId, Pageable pageable) {
        return null;
    }
}
