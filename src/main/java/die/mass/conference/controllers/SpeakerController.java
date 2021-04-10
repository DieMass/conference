package die.mass.conference.controllers;

import die.mass.conference.config.security.UserDetailsImpl;
import die.mass.conference.dtos.request.UserDto;
import die.mass.conference.dtos.response.Response;
import die.mass.conference.dtos.response.TalkDto;
import die.mass.conference.services.interfaces.TalkService;
import die.mass.conference.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(SpeakerController.ROOT)
public class SpeakerController {

    public static final String ROOT = "/api/speaker";
    public static final String TALKS = "/talks";
    public static final String TURN_INTO_SPEAKER = "/turnIntoSpeaker";

    private final TalkService talkService;
    private final UserService userService;

    @GetMapping(TALKS)
    public Response<List<TalkDto>> getTalks(@AuthenticationPrincipal UserDetailsImpl currentUser) {
        return new Response<>(talkService.getBySpeaker(currentUser.getUser()));
    }

    @PostMapping(TURN_INTO_SPEAKER)
    public Response<Boolean> turnIntoSpeaker(@RequestBody UserDto userDto) {
        return new Response<>(userService.turnIntoSpeaker(userDto.getId()));
    }
}
