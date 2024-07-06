package com.localWeb.localWeb.models.dto.response;

import com.localWeb.localWeb.enums.PhonableType;
import com.localWeb.localWeb.models.dto.common.CountryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneResponse {
    private CountryDTO country;
    private String number;
    private UUID phoneable;
    private PhonableType phonableType;
}
