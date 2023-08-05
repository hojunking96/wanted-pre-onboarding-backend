package api.wantedpreonboardingbackend.domain.member.service;

import api.wantedpreonboardingbackend.domain.member.entity.Member;
import api.wantedpreonboardingbackend.domain.member.repository.MemberRepository;
import api.wantedpreonboardingbackend.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public Member join(String email, String password) {
        Member member = memberRepository.findByEmail(email).orElse(null);
        if (member != null) {
            return null;
        }
        return memberRepository.save(Member.of(email, passwordEncoder.encode(password)));
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email).orElse(null);
    }

    public String generateJwtToken(Member member, String password) {
        if (!passwordEncoder.matches(password, member.getPassword())) {
            return null;
        }
        return jwtProvider.generateToken(member.toClaims(), 60 * 60 * 24 * 365);
    }
}
