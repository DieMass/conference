package die.mass.conference.dtos.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TalkUpdatingDto {
    private Long talkId;
    private String title;
    private String description;
    private Long conferenceId;
}