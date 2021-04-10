package die.mass.conference.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import die.mass.conference.models.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {


}