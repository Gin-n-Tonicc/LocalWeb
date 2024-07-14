package com.localWeb.localWeb.services.impl;


import com.localWeb.localWeb.exceptions.lesson.LessonNotFoundException;
import com.localWeb.localWeb.exceptions.organisation.OrganisationNotFoundException;
import com.localWeb.localWeb.models.dto.request.LessonRequestDTO;
import com.localWeb.localWeb.models.dto.response.LessonResponseDTO;
import com.localWeb.localWeb.models.entity.Lesson;
import com.localWeb.localWeb.models.entity.Organisation;
import com.localWeb.localWeb.repositories.LessonRepository;
import com.localWeb.localWeb.repositories.OrganisationRepository;
import com.localWeb.localWeb.services.LessonService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;
    private final ModelMapper modelMapper;
    private final OrganisationRepository organisationRepository;
    private final MessageSource messageSource;

    @Override
    public List<LessonResponseDTO> getAllLessons() {
        List<Lesson> lessons = lessonRepository.findAllByDeletedAtIsNull();
        return lessons.stream().map(lesson -> modelMapper.map(lesson, LessonResponseDTO.class)).toList();
    }

    @Override
    public List<LessonResponseDTO> getAllByOrganisation(UUID id) {
        List<Lesson> lessons = lessonRepository.findAllByOrganisationIdAndDeletedAtIsNull(id);
        return lessons.stream().map(lesson -> modelMapper.map(lesson, LessonResponseDTO.class)).toList();
    }

    @Override
    public LessonResponseDTO getLessonById(UUID id) {
        Optional<Lesson> lesson = lessonRepository.findByIdAndDeletedAtIsNull(id);
        if (lesson.isPresent()) {
            return modelMapper.map(lesson.get(), LessonResponseDTO.class);
        }
        throw new LessonNotFoundException(messageSource);
    }

    @Override
    public LessonResponseDTO createLesson(LessonRequestDTO lessonDTO) {
        Organisation organisation = organisationRepository.findByIdAndDeletedAtIsNull(lessonDTO.getOrganisation())
                .orElseThrow(() -> new OrganisationNotFoundException(messageSource));

        Lesson lessonEntity = modelMapper.map(lessonDTO, Lesson.class);
        lessonEntity.setOrganisation(organisation);
        lessonEntity = lessonRepository.save(lessonEntity);

        return modelMapper.map(lessonEntity, LessonResponseDTO.class);
    }

    @Override
    public LessonResponseDTO updateLesson(UUID id, LessonRequestDTO lessonDTO) {
        Lesson existingLesson = lessonRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new LessonNotFoundException(messageSource));

        Organisation organisation = organisationRepository.findByIdAndDeletedAtIsNull(lessonDTO.getOrganisation())
                .orElseThrow(() -> new OrganisationNotFoundException(messageSource));

        modelMapper.map(lessonDTO, existingLesson);

        existingLesson.setOrganisation(organisation);

        Lesson updatedLesson = lessonRepository.save(existingLesson);

        return modelMapper.map(updatedLesson, LessonResponseDTO.class);
    }


    @Override
    public void deleteLesson(UUID id) {
        Optional<Lesson> lesson = lessonRepository.findByIdAndDeletedAtIsNull(id);
        if (lesson.isPresent()) {
            lesson.get().setDeletedAt(LocalDateTime.now());
            lessonRepository.save(lesson.get());
        } else {
            throw new LessonNotFoundException(messageSource);
        }
    }
}