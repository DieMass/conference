package die.mass.conference.controllers.user;

import die.mass.conference.dtos.request.UserDto;
import die.mass.conference.dtos.response.Response;
import die.mass.conference.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(UserController.ROOT)
public class UserController {

    public final static String ROOT = "/api/users";
    public final static String ALL = "/all";
    public final static String BY_EMAIL = "/email";
    public final static String UPDATE = "/update";
    public final static String DELETE = "/delete";
    public final static String CREATE = "/create";

    private final UserService userService;

    @GetMapping(ALL)
    public Response<List<UserDto>> getAll() {
        return Response.<List<UserDto>>builder().result(userService.findAll()).build();
    }

    @GetMapping(BY_EMAIL)
    public Response<UserDto> getByEmail(@RequestParam String email) {
        return Response.<UserDto>builder().result(userService.findByEmail(email)).build();
    }

    @PutMapping(UPDATE)
    public Response<UserDto> update(@RequestBody UserDto userDto) {
        return Response.<UserDto>builder().result(userService.update(userDto)).build();
    }

    @PostMapping(DELETE)
    public Response<Boolean> deleteUser(@RequestBody UserDto userDto) {
        return Response.<Boolean>builder().result(userService.delete(userDto)).build();
    }

    @PostMapping(CREATE)
    public Response<Boolean> createUser(@RequestBody UserDto userDto) {
        return Response.<Boolean>builder().result(userService.delete(userDto)).build();
    }

}
