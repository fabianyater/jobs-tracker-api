package com.api.jobstracker.application.service;

import com.api.jobstracker.infrastructure.model.request.CommentRequest;
import com.api.jobstracker.infrastructure.model.response.CommentResponse;

import java.awt.print.Pageable;
import java.util.List;

public interface CommentService {
    CommentResponse addComment(CommentRequest commentRequest);
    List<CommentResponse> getAllComments(Integer applicationId, Pageable pageable);
}
