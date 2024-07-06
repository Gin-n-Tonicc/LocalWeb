package com.localWeb.localWeb.models.dto.common;

import com.localWeb.localWeb.enums.AddressableType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private String line;
    private UUID cityId;
    private String lat;
    private String lng;
    private UUID addressableId;
    private AddressableType addressableType;
}
