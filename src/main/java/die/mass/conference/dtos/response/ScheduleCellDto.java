package die.mass.conference.dtos.response;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ScheduleCellDto {
    private String room;
    private Long roomId;
    private String conference;
    private String talkTitle;
    private String talkDescription;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
}