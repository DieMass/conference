package die.mass.conference.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class ScheduleCell {

    private static final String ROOM_ID_COLUMN = "room_id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = ROOM_ID_COLUMN, insertable = false, updatable = false)
    private Room room;
    @Column(name = ROOM_ID_COLUMN)
    private Long roomId;

    @OneToOne
    private Talk talk;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
}