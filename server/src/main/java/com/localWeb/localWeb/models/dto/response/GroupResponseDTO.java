package com.localWeb.localWeb.models.dto.response;

import com.localWeb.localWeb.models.dto.auth.PublicUserDTO;
import com.localWeb.localWeb.models.dto.common.GroupDTO;
import com.localWeb.localWeb.models.dto.common.LessonDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupResponseDTO extends GroupDTO {
    private LessonDTO group;
    private PublicUserDTO owner;
    private String slug;
    private Set<PublicUserDTO> users;
}