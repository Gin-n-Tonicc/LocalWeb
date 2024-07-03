package com.localWeb.localWeb.models.entity;

import com.localWeb.localWeb.models.baseEntity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "files")
public class File extends BaseEntity {
    @NotNull(message = "The name of the file should not be null!")
    private String name;
    private String type;
    @NotNull(message = "The path of the file should not be null!")
    private String path;
    private Long size;
}
