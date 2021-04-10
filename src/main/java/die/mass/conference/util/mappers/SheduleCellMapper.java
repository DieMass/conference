package die.mass.conference.util.mappers;

import die.mass.conference.dtos.request.TalkTimeDto;
import die.mass.conference.dtos.response.ScheduleCellDto;
import die.mass.conference.models.ScheduleCell;
import die.mass.conference.models.Talk;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SheduleCellMapper {

    @Mapping(target = "talkTitle", source = "talk.title")
    @Mapping(target = "talkDescription", source = "talk.description")
    @Mapping(target = "room", source = "room.name")
    @Mapping(target = "conference", source = "scheduleCell.talk.conference.title")
    ScheduleCellDto scheduleCellToScheduleCellDto(ScheduleCell scheduleCell);

    @Mapping(target = "talk", source = "talk")
    @Mapping(target = "roomId", source = "dto.roomId")
    @Mapping(target = "beginTime", source = "dto.beginTime")
    @Mapping(target = "endTime", source = "dto.endTime")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "room", ignore = true)
    ScheduleCell scheduleCellDtoAndTAlkToScheduleCell(TalkTimeDto dto, Talk talk);
}
