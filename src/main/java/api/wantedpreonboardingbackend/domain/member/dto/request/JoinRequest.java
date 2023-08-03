package api.wantedpreonboardingbackend.domain.member.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JoinRequest {

    @ValidEmail
    private String email;
    @Size(min = 8, message = "8자 미만의 비밀번호")
    private String password;
}
