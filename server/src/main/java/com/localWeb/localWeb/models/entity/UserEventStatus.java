package com.localWeb.localWeb.models.entity;
import com.localWeb.localWeb.enums.Status;
import com.localWeb.localWeb.models.baseEntity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_event_status")
public class UserEventStatus extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull(message = "The owner of the comment should not be null!")
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Enumerated(EnumType.STRING)
    private Status status;
}
