package api.wantedpreonboardingbackend.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String email;
    private String password;

    public static Member of(String email, String password) {
        return Member.builder()
                .email(email)
                .password(password)
                .build();
    }
}
