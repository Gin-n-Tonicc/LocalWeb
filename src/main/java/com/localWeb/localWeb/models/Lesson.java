package com.localWeb.localWeb.models;

import com.localWeb.localWeb.models.baseEntity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "lessons")
public class Lesson extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "organisation_id")
    @NotNull(message = "The organisation of the lesson should not be null!")
    private Organisation organisation;

    @NotNull(message = "The name should not be null!")
    private String name;

    private String slug;

    @NotNull(message = "The description should not be null!")
    @Size(min = 60, max = 400, message = "The description must be between 60 and 400 symbols!")
    private String description;

    @ManyToMany(mappedBy = "lessons")
    @ToString.Exclude
    private Set<User> users = new HashSet<>();
}
