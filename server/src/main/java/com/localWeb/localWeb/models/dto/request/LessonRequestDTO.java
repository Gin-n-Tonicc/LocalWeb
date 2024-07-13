package com.localWeb.localWeb.models.dto.request;

import com.localWeb.localWeb.models.dto.common.LessonDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonRequestDTO extends LessonDTO {
    private UUID organisationId;
}
