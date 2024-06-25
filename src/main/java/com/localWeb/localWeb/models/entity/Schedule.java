package com.localWeb.localWeb.models.entity;

import com.localWeb.localWeb.models.baseEntity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "schedules")
public class Schedule extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "parent_schedule_id")
    private Schedule parentSchedule;

    @ManyToOne
    @JoinColumn(name = "group_id")
    @NotNull(message = "The group of the schedule should not be null!")
    private Group group;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    private int recurrence;

    private boolean isPublished;
}
