package die.mass.conference.util.mappers;

import die.mass.conference.dtos.request.UserDto;
import die.mass.conference.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "role", source = "role")
    UserDto userDtoFromUser(User user);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "password", ignore = true)
    User userFromUserDto(UserDto userDto);
}
