package die.mass.conference.repositories;

import die.mass.conference.models.ScheduleCell;
import die.mass.conference.models.Talk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<ScheduleCell, Long> {

    @Query(value = "select sc from ScheduleCell sc where sc.room.id = :roomId and " +
            "(:beginTime between sc.beginTime and sc.endTime " +
            "or :endTime between sc.beginTime and sc.endTime)")
    Optional<ScheduleCell> findByTime(@Param("beginTime") LocalDateTime beginTime,
                                      @Param("endTime") LocalDateTime endTime,
                                      @Param("roomId") Long roomId);

    Optional<ScheduleCell> findFirstByTalk_IdAndTalk_Conference_Id(Long talkId, Long conferenceId);

    List<ScheduleCell> findAllByTalkIn(List<Talk> talks);
}