package die.mass.conference.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Talk {

    private static final String CONFERENCE_ID_COLUMN = "conference_id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = CONFERENCE_ID_COLUMN, insertable = false, updatable = false)
    private Conference conference;
    @Column(name = CONFERENCE_ID_COLUMN)
    private Long conferenceId;

    @ManyToMany
    private List<User> speakers;
}