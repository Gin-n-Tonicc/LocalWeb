package com.localWeb.localWeb.services;

import com.localWeb.localWeb.models.dto.request.LessonRequest;
import com.localWeb.localWeb.models.dto.response.LessonResponse;

import java.util.List;
import java.util.UUID;

public interface LessonService {

    List<LessonResponse> getAllLessons();

    LessonResponse getLessonById(UUID id);

    LessonResponse createLesson(LessonRequest lessonDTO);

    LessonResponse updateLesson(UUID id, LessonRequest lessonDTO);

    void deleteLesson(UUID id);
}