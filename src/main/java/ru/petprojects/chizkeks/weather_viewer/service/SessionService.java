package ru.petprojects.chizkeks.weather_viewer.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import ru.petprojects.chizkeks.weather_viewer.model.Session;
import ru.petprojects.chizkeks.weather_viewer.model.dto.UserDto;
import ru.petprojects.chizkeks.weather_viewer.model.mapper.UserMapper;
import ru.petprojects.chizkeks.weather_viewer.repository.SessionRepository;
import ru.petprojects.chizkeks.weather_viewer.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Setter
@Getter
@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public Session create(UserDto user) {
        Session newSession = Session.builder()
                .id(UUID.randomUUID().toString())
                .user(userMapper.userDtoToUser(user))
                .expiresAt(LocalDateTime.now().plusHours(5))
                .build();
        sessionRepository.findById(newSession.getId());
        return sessionRepository.saveAndFlush(newSession);
        //return sessionRepository.save(newSession);
    }

    public Session getById(String id) {
        return sessionRepository.findById(id.toString());
    }
}
