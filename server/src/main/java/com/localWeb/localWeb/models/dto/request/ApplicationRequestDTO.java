package com.localWeb.localWeb.models.dto.request;

import com.localWeb.localWeb.models.dto.common.ApplicationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationRequestDTO extends ApplicationDTO {
    private UUID lesson;
}
