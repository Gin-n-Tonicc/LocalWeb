package com.localWeb.localWeb.services;

import com.localWeb.localWeb.enums.ApplicationStatus;
import com.localWeb.localWeb.models.dto.auth.PublicUserDTO;
import com.localWeb.localWeb.models.dto.request.ApplicationRequestDTO;
import com.localWeb.localWeb.models.dto.response.ApplicationResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface ApplicationService {
    ApplicationResponseDTO applyForLesson(PublicUserDTO loggedUser, ApplicationRequestDTO applicationRequestDTO);

    List<ApplicationResponseDTO> getPendingApplications(UUID lessonId);

    ApplicationResponseDTO updateApplicationStatus(UUID applicationId, ApplicationStatus status, UUID groupId, PublicUserDTO publicUserDTO);
}
