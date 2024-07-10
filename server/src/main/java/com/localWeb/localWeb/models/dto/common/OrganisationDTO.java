package com.localWeb.localWeb.models.dto.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganisationDTO {
    private String name;
    private String description;
    private String email;
    private String websiteUrl;
}
