package com.localWeb.localWeb.services.impl;

import com.localWeb.localWeb.enums.ApplicationStatus;
import com.localWeb.localWeb.exceptions.application.ApplicationNotFoundException;
import com.localWeb.localWeb.exceptions.group.GroupNotFoundException;
import com.localWeb.localWeb.exceptions.lesson.LessonNotFoundException;
import com.localWeb.localWeb.exceptions.user.UserNotFoundException;
import com.localWeb.localWeb.models.dto.auth.PublicUserDTO;
import com.localWeb.localWeb.models.dto.request.ApplicationRequestDTO;
import com.localWeb.localWeb.models.dto.response.ApplicationResponseDTO;
import com.localWeb.localWeb.models.entity.Application;
import com.localWeb.localWeb.models.entity.Group;
import com.localWeb.localWeb.models.entity.Lesson;
import com.localWeb.localWeb.models.entity.User;
import com.localWeb.localWeb.repositories.ApplicationRepository;
import com.localWeb.localWeb.repositories.GroupRepository;
import com.localWeb.localWeb.repositories.LessonRepository;
import com.localWeb.localWeb.repositories.UserRepository;
import com.localWeb.localWeb.services.ApplicationService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;

    private final LessonRepository lessonRepository;

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    private final MessageSource messageSource;

    private final GroupRepository groupRepository;

    @Override
    public ApplicationResponseDTO applyForLesson(PublicUserDTO loggedUser, ApplicationRequestDTO applicationRequestDTO) {
        User user = modelMapper.map(loggedUser, User.class);

        Lesson lesson = lessonRepository.findByIdAndDeletedAtIsNull(applicationRequestDTO.getLesson()).orElseThrow(() -> new LessonNotFoundException(messageSource));

        Application application = modelMapper.map(applicationRequestDTO, Application.class);
        application.setUser(user);
        application.setLesson(lesson);
        application.setStatus(ApplicationStatus.PENDING);

        application = applicationRepository.save(application);

        return modelMapper.map(application, ApplicationResponseDTO.class);
    }

    @Override
    public List<ApplicationResponseDTO> getPendingApplications(UUID lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new LessonNotFoundException(messageSource));

        List<Application> pendingApplications = applicationRepository.findByLessonAndStatus(lesson, ApplicationStatus.PENDING);
        return pendingApplications.stream().map(application -> modelMapper.map(application, ApplicationResponseDTO.class)).toList();
    }

    @Override
    public ApplicationResponseDTO updateApplicationStatus(UUID applicationId, ApplicationStatus status, UUID groupId, PublicUserDTO publicUserDTO) {
        Application application = applicationRepository.findById(applicationId).orElseThrow(() -> new ApplicationNotFoundException(messageSource));

        User loggedUser = modelMapper.map(publicUserDTO, User.class);

//        if (!(application.getLesson().getOrganisation().getOwners().contains(loggedUser)) && !(loggedUser.getRole().equals(Role.ADMIN))) {
//            throw new AccessDeniedException(messageSource);
//        }

        Lesson lesson = lessonRepository.findByIdAndDeletedAtIsNull(application.getLesson().getId()).orElseThrow(() -> new LessonNotFoundException(messageSource));

        User applicant = userRepository.findById(application.getUser().getId()).orElseThrow(() -> new UserNotFoundException(messageSource));

        application.setStatus(status);

        if (status.equals(ApplicationStatus.ACCEPTED)) {
            acceptApplication(lesson, applicant, groupId);
        }

        application = applicationRepository.save(application);
        return modelMapper.map(application, ApplicationResponseDTO.class);
    }

    private void acceptApplication(Lesson lesson, User applicant, UUID groupId) {
        Group group = groupRepository.findByIdAndDeletedAtIsNull(groupId).orElseThrow(() -> new GroupNotFoundException(messageSource));

        applicant.getLessons().add(lesson);
        applicant.getGroups().add(group);

        userRepository.save(applicant);
    }
}
