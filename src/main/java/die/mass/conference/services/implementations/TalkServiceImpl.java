package die.mass.conference.services.implementations;

import die.mass.conference.controllers.NotFoundEntityException;
import die.mass.conference.dtos.request.TalkCreationDto;
import die.mass.conference.dtos.request.TalkDeletingDto;
import die.mass.conference.dtos.request.TalkTimeDto;
import die.mass.conference.dtos.request.TalkUpdatingDto;
import die.mass.conference.dtos.response.TalkDto;
import die.mass.conference.models.*;
import die.mass.conference.repositories.*;
import die.mass.conference.services.interfaces.*;
import die.mass.conference.util.mappers.SheduleCellMapper;
import die.mass.conference.util.mappers.TalkMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Map.Entry;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
public class TalkServiceImpl implements TalkService {

    private final TalkRepository talkRepository;
    private final UserService userService;
    private final ScheduleCellService scheduleCellService;
    private final ConferenceService conferenceService;

    private final TalkMapper talkMapper;
    private final SheduleCellMapper sheduleCellMapper;

    @Transactional
    public TalkDto save(TalkCreationDto dto, Long userId) {
        final User user = userService.findById(userId);
        final Conference conference = conferenceService.getConference(dto.getConferenceId());
        final Talk talk = talkMapper.talkDtoToTalk(dto, conference, Collections.singletonList(user));
        talkRepository.save(talk);
        conferenceService.addTalk(conference, talk);
        return talkMapper.dtoFromTalk(talk);
    }

    public Talk findById(Long id) {
        return talkRepository.findById(id).orElseThrow(NotFoundEntityException::new);
    }

    @Override
    public boolean isClear(LocalDateTime beginTime, LocalDateTime endTime, Long roomId) {
        Optional<ScheduleCell> scheduleCellCandidate = scheduleCellService.findByTime(beginTime, endTime, roomId);
        return scheduleCellCandidate.isEmpty();
    }

    @Override
    @Transactional
    public boolean changeTime(TalkTimeDto dto) {
        if (!isClear(dto.getBeginTime(), dto.getEndTime(), dto.getRoomId())) {
            return false;
        }
        final Talk talk = findById(dto.getTalkId());
        final Optional<ScheduleCell> scheduleCellCandidate = scheduleCellService
                .getScheduleCellByTalkId(dto.getTalkId(), talk.getConference().getId());
        if (scheduleCellCandidate.isPresent()) {
            final ScheduleCell scheduleCell = scheduleCellCandidate.get();
            scheduleCellService.updateTime(scheduleCell, dto.getBeginTime(), dto.getEndTime());
        } else {
            scheduleCellService.save(sheduleCellMapper.scheduleCellDtoAndTAlkToScheduleCell(dto, talk));
        }
        return true;
    }

    @Override
    public List<TalkDto> getBySpeaker(User speaker) {
        final List<Talk> talks = talkRepository.findBySpeakersIsContaining(speaker);
        Map<Talk, List<ScheduleCell>> cells = scheduleCellService.findAllByTalkIn(talks).stream()
                .collect(groupingBy(ScheduleCell::getTalk, toList()));
        List<TalkDto> dtos = new ArrayList<>();
        for (Talk talk : talks) {
            List<ScheduleCell> scheduleCells = Optional.ofNullable(cells.get(talk)).orElse(Collections.singletonList(ScheduleCell.builder().build()));
            scheduleCells.forEach(i -> dtos.add(talkMapper.dtoFromTalkAndCell(talk, i)));
        }
        return dtos;
    }

    @Override
    public TalkDto updateTalk(TalkUpdatingDto talkUpdatingDto) {
        final Talk talk = talkRepository.findById(talkUpdatingDto.getTalkId()).orElseThrow(EntityNotFoundException::new);
        return talkMapper.dtoFromTalk(talkRepository.save(talkMapper.talkFromUpdatingDto(talkUpdatingDto, talk)));
    }

    @Override
    public Boolean deleteTalk(TalkDeletingDto talkDeletingDto) {
         talkRepository.deleteById(talkDeletingDto.getTalkId());
        return true;
    }
}