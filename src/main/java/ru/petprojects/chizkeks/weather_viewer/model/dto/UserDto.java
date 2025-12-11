package ru.petprojects.chizkeks.weather_viewer.model.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @Size(min = 4, max = 20)
    private String login;

    private String password;
}
