package die.mass.conference.models;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(value = EnumType.STRING)
    @Setter
    private Role role;
    private String email;
    private String password;

    public enum Role {
        ADMIN, SPEAKER, LISTENER
    }
}