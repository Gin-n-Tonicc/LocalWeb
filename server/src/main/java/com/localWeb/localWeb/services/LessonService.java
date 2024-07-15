package com.localWeb.localWeb.services;

import com.localWeb.localWeb.models.dto.request.LessonRequestDTO;
import com.localWeb.localWeb.models.dto.response.LessonResponseDTO;

import java.util.List;
import java.util.UUID;

public interface LessonService {

    List<LessonResponseDTO> getAllLessons();

    LessonResponseDTO getLessonById(UUID id);

    LessonResponseDTO createLesson(LessonRequestDTO lessonDTO);

    LessonResponseDTO updateLesson(UUID id, LessonRequestDTO lessonDTO);

    void deleteLesson(UUID id);

    List<LessonResponseDTO> getAllByOrganisation(UUID id);
}