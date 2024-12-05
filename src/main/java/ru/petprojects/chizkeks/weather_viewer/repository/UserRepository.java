package ru.petprojects.chizkeks.weather_viewer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.petprojects.chizkeks.weather_viewer.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
