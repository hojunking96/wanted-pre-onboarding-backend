package api.wantedpreonboardingbackend.domain.member.service;

import api.wantedpreonboardingbackend.domain.member.entity.Member;
import api.wantedpreonboardingbackend.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.MethodName.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MemberServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("join 성공 테스트")
    void joinSuccessTest() {
        // Given
        String email = "user1@test.com";
        String password = "12345678";

        // When
        Member savedMember = memberService.join(email, password);

        // Then
        assertThat(savedMember.getId()).isEqualTo(1);
        assertThat(savedMember.getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("join 실패 테스트 - 중복 이메일 가입")
    void joinFailureTest() {
        // Given
        String email = "user1@test.com";
        String password = "12345678";
        memberRepository.save(Member.of(email, passwordEncoder.encode(password)));

        // When
        Member savedMember = memberService.join(email, password);

        // Then
        assertThat(savedMember).isNull();
    }

    @Test
    @DisplayName("findByEmail 성공 테스트")
    void findByEmailSuccessTest() {
        // Given
        String email = "user1@test.com";
        String password = "12345678";
        memberRepository.save(Member.of(email, passwordEncoder.encode(password)));

        // When
        Member member = memberService.findByEmail(email);

        // Then
        assertThat(member.getId()).isEqualTo(1);
        assertThat(member.getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("findByEmail 실패 테스트 - 존재하지 않는 이메일 검색")
    void findByEmailFailureTest() {
        // Given
        String email = "user1@test.com";
        String password = "12345678";
        memberRepository.save(Member.of(email, passwordEncoder.encode(password)));
        String email2 = "user2@test.com";

        // When
        Member member = memberService.findByEmail(email2);

        // Then
        assertThat(member).isNull();
    }

    @Test
    @DisplayName("generateJwtToken 성공 테스트")
    void generateJwtTokenSuccessTest() {
        // Given
        String email = "user1@test.com";
        String password = "12345678";
        Member member = memberRepository.save(Member.of(email, passwordEncoder.encode(password)));

        // When
        String jwtToken = memberService.generateJwtToken(member, password);

        // Then
        System.out.println(jwtToken);
        assertThat(jwtToken).isNotNull();
    }

    @Test
    @DisplayName("generateJwtToken 실패 테스트 - 비밀번호 불일치")
    void generateJwtTokenFailureTest() {
        // Given
        String email = "user1@test.com";
        String password = "12345678";
        String password2 = "87654321";
        Member member = memberRepository.save(Member.of(email, passwordEncoder.encode(password)));

        // When
        String jwtToken = memberService.generateJwtToken(member, password2);

        // Then
        assertThat(jwtToken).isNull();
    }

    @Test
    @DisplayName("findById 성공 테스트")
    void findByIdSuccessTest() {
        // Given
        String email = "user1@test.com";
        String password = "12345678";
        Member member = memberRepository.save(Member.of(email, passwordEncoder.encode(password)));

        // When
        Optional<Member> foundMember = memberService.findById(member.getId());

        // Then
        assertTrue(foundMember.isPresent());
        assertThat(foundMember.get().getId()).isEqualTo(1);
        assertThat(foundMember.get().getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("findById 실패 테스트")
    void findByIdFailureTest() {
        // Given
        String email = "user1@test.com";
        String password = "12345678";
        memberRepository.save(Member.of(email, passwordEncoder.encode(password)));

        // When
        Optional<Member> foundMember = memberService.findById(2L);

        // Then
        assertTrue(foundMember.isEmpty());
    }
}