package com.localWeb.localWeb.models.dto.response;

import com.localWeb.localWeb.models.dto.auth.PublicUserDTO;
import com.localWeb.localWeb.models.dto.common.LessonDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonResponseDTO extends LessonDTO {
    private UUID id;
    private String slug;
    private OrganisationResponseDTO organisation;
    private Set<PublicUserDTO> users = new HashSet<>();
}
