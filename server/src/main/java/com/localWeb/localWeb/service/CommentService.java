package com.localWeb.localWeb.service;

import com.localWeb.localWeb.models.dto.request.CommentRequest;
import com.localWeb.localWeb.models.dto.response.CommentResponse;

import java.util.List;
import java.util.UUID;

public interface CommentService {

    List<CommentResponse> getAllComments();

    CommentResponse getCommentById(UUID id);

    CommentResponse createComment(CommentRequest commentDTO);

    CommentResponse updateComment(UUID id, CommentRequest commentDTO);

    void deleteComment(UUID id);
}