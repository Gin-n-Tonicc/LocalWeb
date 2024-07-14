package com.localWeb.localWeb.models.entity;

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
@Table(name = "groups")
public class Group extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "lesson_id")
    @NotNull(message = "The lesson of the group should not be null!")
    private Lesson lesson;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull(message = "The owner of the group should not be null!")
    private User owner;

    @NotNull(message = "The name should not be null!")
    private String name;

    private String slug;

    @NotNull(message = "The description should not be null!")
    @Size(min = 60, max = 400, message = "The description must be between 60 and 400 symbols!")
    private String description;

    @ManyToMany(mappedBy = "groups")
    @ToString.Exclude
    private Set<User> users = new HashSet<>();
}
