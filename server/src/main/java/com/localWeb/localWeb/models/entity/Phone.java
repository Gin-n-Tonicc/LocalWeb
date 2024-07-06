package com.localWeb.localWeb.models.entity;

import com.localWeb.localWeb.enums.AddressableType;
import com.localWeb.localWeb.enums.PhonableType;
import com.localWeb.localWeb.models.baseEntity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = "\\d{10}", message = "The phone number should consist of exactly 10 digits.")
    private String number;

    @ManyToOne
    @JoinColumn(name = "phoneable_id")
    @NotNull(message = "The phoneable entity should not be null!")
    private BaseEntity phoneable;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PhonableType phonableType;
}
