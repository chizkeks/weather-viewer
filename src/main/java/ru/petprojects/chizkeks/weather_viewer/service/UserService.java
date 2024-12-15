package ru.petprojects.chizkeks.weather_viewer.service;

import lombok.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
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

    public UserDto createUser(UserDto userDto) {
        return userMapper.userToUserDto(userRepository.save(userMapper.userDtoToUser(userDto)));
    }

    public UserDto findUserByLogin(String login) {
        return userMapper.userToUserDto(userRepository.findByLogin(login));
    }
}
