package com.localWeb.localWeb.service.impl;

import com.localWeb.localWeb.models.dto.request.CommentRequest;
import com.localWeb.localWeb.models.dto.response.CommentResponse;
import com.localWeb.localWeb.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {

    @Override
    public List<CommentResponse> getAllComments() {
        return List.of();
    }

    @Override
    public CommentResponse getCommentById(UUID id) {
        return null;
    }

    @Override
    public CommentResponse createComment(CommentRequest commentDTO) {
        return null;
    }

    @Override
    public CommentResponse updateComment(UUID id, CommentRequest commentDTO) {
        return null;
    }

    @Override
    public void deleteComment(UUID id) {

    }
}
