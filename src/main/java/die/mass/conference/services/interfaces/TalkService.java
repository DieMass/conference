package die.mass.conference.services.interfaces;

import die.mass.conference.dtos.request.TalkCreationDto;
import die.mass.conference.dtos.request.TalkDeletingDto;
import die.mass.conference.dtos.request.TalkTimeDto;
import die.mass.conference.dtos.request.TalkUpdatingDto;
import die.mass.conference.dtos.response.TalkDto;
import die.mass.conference.models.Talk;
import die.mass.conference.models.User;

import java.time.LocalDateTime;
import java.util.List;

public interface TalkService {

    TalkDto save(TalkCreationDto dto, Long userId);

    boolean isClear(LocalDateTime beginTime, LocalDateTime endTime, Long roomId);

    boolean changeTime(TalkTimeDto talkTimeDto);

    List<TalkDto> getBySpeaker(User speaker);

    TalkDto updateTalk(TalkUpdatingDto talkUpdatingDto);

    Boolean deleteTalk(TalkDeletingDto talkDeletingDto);
}
