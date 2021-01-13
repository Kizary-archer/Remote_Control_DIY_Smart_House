package app.services.impl;

import app.dto.UserCreateDto;
import app.dto.UserDto;
import app.dto.UserUpdateDto;
import app.mapper.UserMapper;
import app.repository.RoleRepository;
import app.repository.StatusRepository;
import app.repository.UserRepository;
import app.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor

public class UserServices implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StatusRepository statusRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<UserDto> getUserByLogin(String login) {
        return userRepository.findByLogin(login).map(userMapper::map);
    }

    @Override
    public Optional<UserDto> getUserById(Integer id) {
        return userRepository.findById(id).map(userMapper::map);
    }

    public Optional<List<UserDto>> getAllUsers() {
        return Optional.of(userRepository.findAll())
                .map(userMapper::map);
    }

    @Transactional
    public Optional<UserDto> createUser(UserCreateDto userCreateDto) {
        return roleRepository.findByRole(userCreateDto.getRole())
                .flatMap(userRole -> statusRepository.findByStatus(userCreateDto.getStatus())
                        .map(userStatus -> userMapper.map(userCreateDto)
                                .setPassword(passwordEncoder.encode(userCreateDto.getPassword()))
                                .setRole(userRole)
                                .setStatus(userStatus)))
                .map(userRepository::save)
                .map(userMapper::map);

    }

    @Override
    @Transactional
    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<UserDto> updateUser(UserUpdateDto userUpdateDto) {
        return roleRepository.findByRole(userUpdateDto.getRole())
                .flatMap(userRole -> statusRepository.findByStatus(userUpdateDto.getStatus())
                        .map(userStatus -> userMapper.map(userUpdateDto)
                                .setPassword(passwordEncoder.encode(userUpdateDto.getPassword()))
                                .setRole(userRole)
                                .setStatus(userStatus)))
                .map(userRepository::save)
                .map(userMapper::map);


    }

}
