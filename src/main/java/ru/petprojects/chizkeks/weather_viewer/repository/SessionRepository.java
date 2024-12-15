package ru.petprojects.chizkeks.weather_viewer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.petprojects.chizkeks.weather_viewer.model.Session;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    public Session findById(String id);
}
