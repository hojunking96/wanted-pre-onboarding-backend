package api.wantedpreonboardingbackend.domain.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
    @JsonIgnore
    private String password;

    public static Member of(String email, String password) {
        return Member.builder()
                .email(email)
                .password(password)
                .build();
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("MEMBER"));
        return authorities;
    }

    public Map<String, Object> toClaims() {
        return Map.of(
                "id", getId(),
                "email", getEmail()
        );
    }
}
