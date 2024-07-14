package com.localWeb.localWeb.models.entity;

import com.localWeb.localWeb.enums.ApplicationStatus;
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
@Table(name = "applications")
public class Application extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull(message = "The user should not be null!")
    private User user;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    @NotNull(message = "The lesson should not be null!")
    private Lesson lesson;

    @NotNull(message = "Please provide information about yourself, why you want to sign up for the" +
            " lesson and if you have any special preferences!")
    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "The status should not be null!")
    private ApplicationStatus status;
}
