package com.localWeb.localWeb.models.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityRequest {
    private UUID country;
    private String name;
    private String slug;
}
