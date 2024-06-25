package com.localWeb.localWeb.models.entity;

import com.localWeb.localWeb.models.baseEntity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "categories")
public class Category  extends BaseEntity {
    @NotNull(message = "The name of the category should not be null!")
    private String name;
}
