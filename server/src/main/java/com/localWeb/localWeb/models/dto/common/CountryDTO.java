package com.localWeb.localWeb.models.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryDTO {
    private String name;
    private String alpha2;
    private String alpha3;
    private String phoneCode;
}
