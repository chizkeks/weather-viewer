package ru.petprojects.chizkeks.weather_viewer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sessions")
public class Session {
    @Id
    String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @Column(name = "expires_at")
    LocalDateTime expiresAt;
}
