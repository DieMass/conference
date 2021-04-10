package die.mass.conference.services.implementations;

import die.mass.conference.dtos.response.ScheduleCellDto;
import die.mass.conference.dtos.response.ScheduleDto;
import die.mass.conference.models.ScheduleCell;
import die.mass.conference.models.Talk;
import die.mass.conference.repositories.ScheduleRepository;
import die.mass.conference.services.interfaces.ScheduleCellService;
import die.mass.conference.util.mappers.SheduleCellMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
public class ScheduleCellServiceImpl implements ScheduleCellService {

    private final ScheduleRepository scheduleRepository;
    private final SheduleCellMapper sheduleCellMapper;

    @Override
    public List<ScheduleCell> getAll() {
        return scheduleRepository.findAll();
    }

    @Override
    public Optional<ScheduleCell> getScheduleCellByTalkId(Long talkId, Long conferenceId) {
        return scheduleRepository.findFirstByTalk_IdAndTalk_Conference_Id(talkId, conferenceId);
    }

    @Override
    public ScheduleCell save(ScheduleCell scheduleCell) {
        return scheduleRepository.save(scheduleCell);
    }

    @Override
    public Optional<ScheduleCell> findByTime(LocalDateTime beginTime, LocalDateTime endTime, Long roomId) {
        return scheduleRepository.findByTime(beginTime, endTime, roomId);
    }

    @Override
    public ScheduleCell updateTime(ScheduleCell scheduleCell, LocalDateTime beginTime, LocalDateTime endTime) {
        scheduleCell.setBeginTime(beginTime);
        scheduleCell.setEndTime(endTime);
        return save(scheduleCell);
    }

    @Override
    public ScheduleDto createSchedule() {
        Map<Pair<Long, String>, List<ScheduleCellDto>> roomNameToCellMap = getAll().stream()
                .map(sheduleCellMapper::scheduleCellToScheduleCellDto)
                .collect(groupingBy(i -> Pair.of(i.getRoomId(), i.getRoom()), toList()));

        Map<Long, ScheduleDto.RoomSchedule> roomIdToRoomScheduleMap = new HashMap<>();
        for (Map.Entry<Pair<Long, String>, List<ScheduleCellDto>> entry : roomNameToCellMap.entrySet()) {
            roomIdToRoomScheduleMap.put(entry.getKey().getFirst(), ScheduleDto.RoomSchedule.builder()
                    .roomName(entry.getKey().getSecond())
                    .talks(entry.getValue())
                    .build());
        }
        return ScheduleDto.builder().roomSchedule(List.copyOf(roomIdToRoomScheduleMap.values())).build();
    }

    @Override
    public List<ScheduleCell> findAllByTalkIn(List<Talk> talks) {
        return scheduleRepository.findAllByTalkIn(talks);
    }
}