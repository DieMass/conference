package die.mass.conference.services.implementations;

import die.mass.conference.controllers.NotFoundEntityException;
import die.mass.conference.dtos.request.SignInDto;
import die.mass.conference.dtos.request.UserDto;
import die.mass.conference.models.User;
import die.mass.conference.repositories.UserRepository;
import die.mass.conference.services.interfaces.UserService;
import die.mass.conference.util.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::userDtoFromUser)
                .collect(Collectors.toList());
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(NotFoundEntityException::new);
    }

    @Override
    public UserDto findByEmail(String email) {
        return userMapper.userDtoFromUser(userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public UserDto update(UserDto userDto) {
        return userMapper.userDtoFromUser(userRepository.save(userMapper.userFromUserDto(userDto)));
    }

    @Override
    public Boolean delete(UserDto userDto) {
        userRepository.deleteById(userDto.getId());
        return true;
    }

    @Override
    public boolean turnIntoSpeaker(Long userId) {
        final User user = findById(userId);
        user.setRole(User.Role.SPEAKER);
        userRepository.save(user);
        return true;
    }

    @Override
    public UserDto create(SignInDto signInDto) {
        return null;
    }
}