package ru.petprojects.chizkeks.weather_viewer.service;

import lombok.*;
import org.springframework.stereotype.Service;
import ru.petprojects.chizkeks.weather_viewer.model.User;
import ru.petprojects.chizkeks.weather_viewer.model.dto.UserDto;
import ru.petprojects.chizkeks.weather_viewer.model.mapper.UserMapper;
import ru.petprojects.chizkeks.weather_viewer.repository.UserRepository;

@RequiredArgsConstructor
@Setter
@Getter
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User createUser(UserDto userDto) {
        return userRepository.save(userMapper.userDtoToUser(userDto));
    }

    public User findUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }
}
