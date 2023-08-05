package api.wantedpreonboardingbackend.domain.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private final String jwtToken;

    public static LoginResponse of(String jwtToken) {
        return new LoginResponse(jwtToken);
    }
}
