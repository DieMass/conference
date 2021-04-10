package die.mass.conference.controllers;

import die.mass.conference.config.security.UserDetailsImpl;
import die.mass.conference.dtos.request.ConferenceDto;
import die.mass.conference.dtos.response.Response;
import die.mass.conference.models.Conference;
import die.mass.conference.models.Talk;
import die.mass.conference.services.interfaces.ConferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping(ConferenceController.ROOT)
public class ConferenceController {


    public static final String ROOT = "/api/conference";
    public static final String TALKS = "/talks";
    public static final String REGISTER = "/register";
    private final ConferenceService conferenceService;

    @GetMapping(TALKS)
    public Response<List<Talk>> getTalks(@RequestBody ConferenceDto conferenceDto) {
        final Conference conference = conferenceService.getConference(conferenceDto.getConferenceId());
            return new Response<>(conference.getTalks());
    }

    @PostMapping(REGISTER)
    public Response<?> register(@RequestBody ConferenceDto conferenceDto,
                                @AuthenticationPrincipal UserDetailsImpl currentUser) {
        try {
            return new Response<>(conferenceService
                    .registerListener(conferenceDto.getConferenceId(), currentUser.getUserId()));
        } catch (NotFoundEntityException e) {
            return new Response<>(Response.Error.builder().type(Response.Error.Type.ENTITY_NOT_FOUND).build());
        }
    }
}
