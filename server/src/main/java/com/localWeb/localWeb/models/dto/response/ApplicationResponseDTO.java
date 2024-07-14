package com.localWeb.localWeb.models.dto.response;

import com.localWeb.localWeb.enums.ApplicationStatus;
import com.localWeb.localWeb.models.dto.auth.PublicUserDTO;
import com.localWeb.localWeb.models.dto.common.ApplicationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationResponseDTO extends ApplicationDTO {
    private UUID id;
    private PublicUserDTO user;
    private LessonResponseDTO lesson;
    private ApplicationStatus status;
}
