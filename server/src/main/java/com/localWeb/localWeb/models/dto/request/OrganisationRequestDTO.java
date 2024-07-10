package com.localWeb.localWeb.models.dto.request;

import com.localWeb.localWeb.models.dto.auth.PublicUserDTO;
import com.localWeb.localWeb.models.dto.common.OrganisationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganisationRequestDTO extends OrganisationDTO {
    private UUID profileImage;
    private Set<UUID> owners = new HashSet<>();
    private Set<UUID> files = new HashSet<>();
}
