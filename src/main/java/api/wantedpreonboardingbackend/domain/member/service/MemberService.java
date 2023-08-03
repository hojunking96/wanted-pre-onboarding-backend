package api.wantedpreonboardingbackend.domain.member.service;

import api.wantedpreonboardingbackend.domain.member.entity.Member;
import api.wantedpreonboardingbackend.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member join(String email, String password) {
        Member member = Member.of(email, password);
        return memberRepository.save(member);

    }
}
