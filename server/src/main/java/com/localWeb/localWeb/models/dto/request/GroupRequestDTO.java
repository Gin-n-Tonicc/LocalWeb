package com.localWeb.localWeb.models.dto.request;

import com.localWeb.localWeb.models.dto.common.GroupDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupRequestDTO extends GroupDTO {
    private UUID lesson;
}
