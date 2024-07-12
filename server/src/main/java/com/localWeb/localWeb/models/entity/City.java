package com.localWeb.localWeb.models.entity;

import com.localWeb.localWeb.models.baseEntity.BaseEntity;
import com.localWeb.localWeb.utils.SlugUtils;
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

    @PrePersist
    @PreUpdate
    private void updateSlug() {
        this.slug = SlugUtils.generateSlug(this.name);
    }
}
