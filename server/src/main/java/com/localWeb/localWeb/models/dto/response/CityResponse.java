package com.localWeb.localWeb.models.dto.response;

import com.localWeb.localWeb.models.dto.common.CountryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityResponse {
    private UUID id;
    private CountryDTO country;
    private String name;
    private String slug;
}
