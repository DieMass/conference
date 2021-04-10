package die.mass.conference.controllers;

import die.mass.conference.config.security.UserDetailsImpl;
import die.mass.conference.dtos.request.TalkCreationDto;
import die.mass.conference.dtos.request.TalkDeletingDto;
import die.mass.conference.dtos.request.TalkTimeDto;
import die.mass.conference.dtos.request.TalkUpdatingDto;
import die.mass.conference.dtos.response.Response;
import die.mass.conference.dtos.response.TalkDto;
import die.mass.conference.services.interfaces.TalkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;

@RestController
@RequiredArgsConstructor
@RequestMapping(TalkController.ROOT)
public class TalkController {

    public static final String ROOT = "/api/talks";
    public static final String CREATE = "/create";
    public static final String UPDATE = "/update";
    public static final String DELETE = "/delete";
    public static final String SET_TIME = "/setTime";

    private final TalkService talkService;

    @PostMapping(CREATE)
    public Response<TalkDto> createTalk(@RequestBody TalkCreationDto dto, @AuthenticationPrincipal UserDetailsImpl user) {
        return Response.<TalkDto>builder().result(talkService.save(dto, user.getUserId())).build();
    }

    @PostMapping(SET_TIME)
    public Response<Boolean> setTime(@RequestBody TalkTimeDto dto) {
        return Response.<Boolean>builder().result(talkService.changeTime(dto)).build();
    }

    @PutMapping(UPDATE)
    public Response<TalkDto> updateTalk(@RequestBody TalkUpdatingDto talkUpdatingDto) {
        return Response.<TalkDto>builder().result(talkService.updateTalk(talkUpdatingDto)).build();
    }

    @PostMapping(DELETE)
    public Response<Boolean> deleteTalk(@RequestBody TalkDeletingDto talkDeletingDto) {
        return Response.<Boolean>builder().result(talkService.deleteTalk(talkDeletingDto)).build();
    }
}