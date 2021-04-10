package die.mass.conference.dtos.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TalkDeletingDto {
    private Long talkId;
}