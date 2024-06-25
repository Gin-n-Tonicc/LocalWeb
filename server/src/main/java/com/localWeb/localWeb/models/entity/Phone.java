package com.localWeb.localWeb.models.entity;

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
@Table(name = "phones")
public class Phone extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "country_id")
    @NotNull(message = "The country of the phone should not be null!")
    private Country country;

    @NotNull(message = "The phone number should not be null!")
    private String number;

    @ManyToOne
    @JoinColumn(name = "phoneable_id")
    @NotNull(message = "The phoneable entity should not be null!")
    private BaseEntity phoneable;
}
