package die.mass.conference.util.mappers;

import die.mass.conference.dtos.request.TalkCreationDto;
import die.mass.conference.dtos.request.TalkUpdatingDto;
import die.mass.conference.dtos.response.TalkDto;
import die.mass.conference.models.Conference;
import die.mass.conference.models.Talk;
import die.mass.conference.models.ScheduleCell;
import die.mass.conference.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TalkMapper {

    @Mapping(target = "speakers", source = "speakers")
    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "conference", source = "conference")
    @Mapping(target = "id", ignore = true)
    Talk talkDtoToTalk(TalkCreationDto dto, Conference conference, List<User> speakers);

    @Mapping(target = "room", source = "scheduleCell.room.name")
    @Mapping(target = "conference", source = "talk.conference.title")
    TalkDto dtoFromTalkAndCell(Talk talk, ScheduleCell scheduleCell);

    @Mapping(target = "conference", source = "talk.conference.title")
    @Mapping(target = "beginTime", ignore = true)
    @Mapping(target = "endTime", ignore = true)
    @Mapping(target = "room", ignore = true)
    TalkDto dtoFromTalk(Talk talk);

    @Mapping(target = "id", source = "talk.id")
    @Mapping(target = "conference", source = "talk.conference")
    @Mapping(target = "speakers", source = "talk.speakers")
    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "conferenceId", source = "dto.conferenceId")
    Talk talkFromUpdatingDto(TalkUpdatingDto dto, Talk talk);
}
