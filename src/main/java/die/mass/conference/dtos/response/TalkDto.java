package die.mass.conference.dtos.response;

import die.mass.conference.models.Talk;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TalkDto {
    private String title;
    private String description;
    private String conference;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private String room;
}