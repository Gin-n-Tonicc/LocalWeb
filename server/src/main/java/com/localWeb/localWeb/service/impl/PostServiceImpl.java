package com.localWeb.localWeb.service.impl;

import com.localWeb.localWeb.models.dto.request.PostRequest;
import com.localWeb.localWeb.models.dto.response.PostResponse;
import com.localWeb.localWeb.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {

    @Override
    public List<PostResponse> getAllPosts() {
        return List.of();
    }

    @Override
    public PostResponse getPostById(UUID id) {
        return null;
    }

    @Override
    public PostResponse createPost(PostRequest postDTO) {
        return null;
    }

    @Override
    public PostResponse updatePost(UUID id, PostRequest postDTO) {
        return null;
    }

    @Override
    public void deletePost(UUID id) {

    }
}