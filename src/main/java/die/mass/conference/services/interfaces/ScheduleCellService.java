package die.mass.conference.services.interfaces;

import die.mass.conference.dtos.response.ScheduleDto;
import die.mass.conference.models.ScheduleCell;
import die.mass.conference.models.Talk;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScheduleCellService {

    List<ScheduleCell> getAll();

    Optional<ScheduleCell> getScheduleCellByTalkId(Long talkId, Long conferenceId);

    ScheduleCell save(ScheduleCell scheduleCell);

    Optional<ScheduleCell> findByTime(LocalDateTime beginTime, LocalDateTime endTime, Long roomId);

    ScheduleCell updateTime(ScheduleCell scheduleCell, LocalDateTime beginTime, LocalDateTime endTime);

    ScheduleDto createSchedule();

    List<ScheduleCell> findAllByTalkIn(List<Talk> talks);
}
