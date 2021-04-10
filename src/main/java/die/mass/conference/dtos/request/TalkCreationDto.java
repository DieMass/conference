package die.mass.conference.dtos.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TalkCreationDto {
    private String title;
    private String description;
    private Long conferenceId;
}