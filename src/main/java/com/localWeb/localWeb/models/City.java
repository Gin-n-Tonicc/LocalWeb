package com.localWeb.localWeb.models;

import com.localWeb.localWeb.models.baseEntity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "cities")
public class City extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "country_id")
    @NotNull(message = "The country of the city should not be null!")
    private Country country;

    @NotNull(message = "The name of the city should not be null!")
    private String name;

    @NotNull(message = "The slug of the city should not be null!")
    private String slug;
}
