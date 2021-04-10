package die.mass.conference.services.implementations;

import die.mass.conference.controllers.NotFoundEntityException;
import die.mass.conference.models.Conference;
import die.mass.conference.models.Talk;
import die.mass.conference.models.User;
import die.mass.conference.repositories.ConferenceRepository;
import die.mass.conference.services.interfaces.ConferenceService;
import die.mass.conference.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConferenceServiceImpl implements ConferenceService {

    private final ConferenceRepository conferenceRepository;
    private final UserService userService;

    @Override
    public Conference getConference(Long id) {
        return conferenceRepository.findById(id).orElseThrow(NotFoundEntityException::new);
    }

    @Override
    public Conference save(Conference conference) {
        return conferenceRepository.save(conference);
    }

    @Override
    public Conference addTalk(Conference conference, Talk talk) {
        conference.getTalks().add(talk);
        save(conference);
        return conference;
    }

    @Override
    public boolean registerListener(Long conferenceId, Long userId) {
        final Conference conference = getConference(conferenceId);
        final User user = userService.findById(userId);
        conference.getListeners().add(user);
        conferenceRepository.save(conference);
        return true;
    }
}
