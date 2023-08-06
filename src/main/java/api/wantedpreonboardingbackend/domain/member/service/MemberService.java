package api.wantedpreonboardingbackend.domain.member.service;

import api.wantedpreonboardingbackend.domain.member.entity.Member;
import api.wantedpreonboardingbackend.domain.member.repository.MemberRepository;
import api.wantedpreonboardingbackend.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public Member join(String email, String password) {
        Member member = memberRepository.findByEmail(email).orElse(null);
        if (member != null) {
            return null;
        }
        return memberRepository.save(Member.of(email, passwordEncoder.encode(password)));
    }

    @Transactional(readOnly = true)
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email).orElse(null);
    }

    @Transactional(readOnly = true)
    public String generateJwtToken(Member member, String password) {
        if (!passwordEncoder.matches(password, member.getPassword())) {
            return null;
        }
        return jwtProvider.generateToken(member.toClaims(), 60 * 60 * 24 * 365);
    }

    @Transactional(readOnly = true)
    public Optional<Member> findById(long id) {
        return memberRepository.findById(id);

    }
}
