package com.localWeb.localWeb.services;

import com.localWeb.localWeb.models.dto.request.PostRequest;
import com.localWeb.localWeb.models.dto.response.PostResponse;

import java.util.List;
import java.util.UUID;

public interface PostService {

    List<PostResponse> getAllPosts();

    PostResponse getPostById(UUID id);

    PostResponse createPost(PostRequest postDTO);

    PostResponse updatePost(UUID id, PostRequest postDTO);

    void deletePost(UUID id);
}