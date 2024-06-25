package com.localWeb.localWeb.service.impl;

import com.localWeb.localWeb.models.dto.request.LessonRequest;
import com.localWeb.localWeb.models.dto.response.LessonResponse;
import com.localWeb.localWeb.service.LessonService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LessonServiceImpl implements LessonService {

    @Override
    public List<LessonResponse> getAllLessons() {
        return List.of();
    }

    @Override
    public LessonResponse getLessonById(UUID id) {
        return null;
    }

    @Override
    public LessonResponse createLesson(LessonRequest lessonDTO) {
        return null;
    }

    @Override
    public LessonResponse updateLesson(UUID id, LessonRequest lessonDTO) {
        return null;
    }

    @Override
    public void deleteLesson(UUID id) {

    }
}
