package die.mass.conference.controllers;

import die.mass.conference.dtos.response.Response;
import die.mass.conference.dtos.response.ScheduleDto;
import die.mass.conference.services.interfaces.ScheduleCellService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping(ScheduleController.ROOT)
public class ScheduleController {

    public static final String ROOT = "/api/schedule";
    private final ScheduleCellService scheduleCellService;

    @GetMapping
    public Response<ScheduleDto> get() {
        return new Response<>(scheduleCellService.createSchedule());
    }
}