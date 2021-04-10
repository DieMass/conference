package die.mass.conference.dtos.request;

import die.mass.conference.models.User;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDto {
    private Long id;
    private String email;
    private User.Role role;
}