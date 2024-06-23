package com.localWeb.localWeb.models;

import com.localWeb.localWeb.models.baseEntity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "countries")
public class Country extends BaseEntity {

    @NotNull(message = "The name of the country should not be null!")
    private String name;

    @NotNull(message = "The name of the country should not be null!")
    @Size(min = 2, max = 2, message = "Alpha2 should be exactly 2 characters!")
    private String alpha2;

    @NotNull(message = "The name of the country should not be null!")
    @Size(min = 3, max = 3, message = "Alpha3 should be exactly 3 characters!")
    private String alpha3;

    @NotNull(message = "The name of the country should not be null!")
    private String phoneCode;
}
