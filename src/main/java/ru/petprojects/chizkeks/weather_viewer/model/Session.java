package ru.petprojects.chizkeks.weather_viewer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "sessions")
public class Session implements BaseEntity<String> {
    @Id
    String id;

    @ManyToOne
    @Cascade(CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    User user;

    @Column(name = "expires_at")
    LocalDateTime expiresAt;
}
