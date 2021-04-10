package die.mass.conference.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import die.mass.conference.models.Conference;

public interface ConferenceRepository extends JpaRepository<Conference, Long> {


}