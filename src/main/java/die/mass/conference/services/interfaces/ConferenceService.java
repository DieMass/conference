package die.mass.conference.services.interfaces;

import die.mass.conference.models.Conference;
import die.mass.conference.models.Talk;

public interface ConferenceService {
    
    Conference getConference(Long id);

    Conference save(Conference conference);

    Conference addTalk(Conference conference, Talk talk);

    boolean registerListener(Long conferenceId, Long userId);

}