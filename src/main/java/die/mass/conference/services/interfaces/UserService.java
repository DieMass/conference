package die.mass.conference.services.interfaces;

import die.mass.conference.dtos.request.SignInDto;
import die.mass.conference.dtos.request.UserDto;
import die.mass.conference.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDto> findAll();

    User findById(Long id);

    UserDto findByEmail(String email);

    UserDto update(UserDto userDto);

    Boolean delete(UserDto userDto);

    boolean turnIntoSpeaker(Long userId);

    UserDto create(SignInDto signInDto);
}
