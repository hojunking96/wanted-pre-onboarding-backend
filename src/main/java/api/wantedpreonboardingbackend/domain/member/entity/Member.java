package api.wantedpreonboardingbackend.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Map;

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
    @Column(unique = true)
    private String email;
    private String password;

    public static Member of(String email, String password) {
        return Member.builder()
                .email(email)
                .password(password)
                .build();
    }

    public Map<String, Object> toClaims() {
        return Map.of(
                "id", getId(),
                "email", getEmail()
        );
    }
}
