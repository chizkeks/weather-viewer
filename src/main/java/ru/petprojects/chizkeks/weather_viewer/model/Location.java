package ru.petprojects.chizkeks.weather_viewer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "locations")
public class Location implements BaseEntity<Long> {
    @Id
    @SequenceGenerator(name = "locations_id_seq", sequenceName = "locations_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "locations_id_seq")
    private Long id;

    private String name;

    @Column(name = "user_id")
    private int userID;

    private double latitude;

    private double longitude;

}
