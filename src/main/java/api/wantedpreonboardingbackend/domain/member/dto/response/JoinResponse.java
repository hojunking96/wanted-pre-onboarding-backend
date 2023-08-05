package api.wantedpreonboardingbackend.domain.member.dto.response;

import api.wantedpreonboardingbackend.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JoinResponse {
    private final Member member;

    public static JoinResponse of(Member member) {
        return new JoinResponse(member);
    }
}
