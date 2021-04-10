package die.mass.conference.repositories;

import die.mass.conference.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import die.mass.conference.models.Talk;

import java.util.List;
import java.util.Optional;

public interface TalkRepository extends JpaRepository<Talk, Long> {

    List<Talk> findBySpeakersIsContaining(User user);
}