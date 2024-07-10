package com.localWeb.localWeb.models.dto.response;

import com.localWeb.localWeb.models.dto.auth.PublicUserDTO;
import com.localWeb.localWeb.models.dto.common.OrganisationDTO;
import com.localWeb.localWeb.models.entity.File;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganisationResponseDTO extends OrganisationDTO {
    private UUID id;
    private File profileImage;
    private String slug;
    private Set<PublicUserDTO> members = new HashSet<>();
    private Set<PublicUserDTO> owners = new HashSet<>();
    private Set<File> files = new HashSet<>();
}
