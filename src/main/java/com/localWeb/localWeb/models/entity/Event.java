package com.localWeb.localWeb.models.entity;

import com.localWeb.localWeb.models.baseEntity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "events")
public class Event extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull(message = "The owner of the event should not be null!")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "organisation_id")
    @NotNull(message = "The organisation of the event should not be null!")
    private Organisation organisation;

    @ManyToOne
    @JoinColumn(name = "file_id")
    @NotNull(message = "The file of the event should not be null!")
    private File file;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @NotNull(message = "The category of the event should not be null!")
    private Category category;

    @NotNull(message = "The name of the event should not be null!")
    private String title;

    @NotNull(message = "The summary of the event should not be null!")
    @Size(min = 30, max = 200, message = "The summary of the event should be between 30 and 200 symbols!")
    private String summary;

    @NotNull(message = "The content of the event should not be null!")
    @Size(min = 100, max = 400, message = "The content of the event should be between 100 and 400 symbols!")
    private String content;

    private boolean isChecked;
}
