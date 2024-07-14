package com.localWeb.localWeb.models.entity;


import com.localWeb.localWeb.enums.AddressableType;
import com.localWeb.localWeb.models.baseEntity.BaseEntity;
import jakarta.persistence.*;
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
@Table(name = "addresses")
public class Address extends BaseEntity {

    @NotNull(message = "Enter some text for the address!")
    @Size(min = 5, message = "The text foe the address should be at least 5 symbols!")
    private String line;

    @ManyToOne
    @JoinColumn(name = "city_id")
    @NotNull(message = "The city of the address should not be null!")
    private City city;

    private String lat;
    private String lng;

    @ManyToOne
    @JoinColumn(name = "addressable_id")
    @NotNull(message = "The addressable entity should not be null!")
    private BaseEntity addressable;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AddressableType addressableType;
}
