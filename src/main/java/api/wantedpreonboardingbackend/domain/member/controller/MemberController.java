package api.wantedpreonboardingbackend.domain.member.controller;

import api.wantedpreonboardingbackend.domain.member.dto.request.LoginRequest;
import api.wantedpreonboardingbackend.domain.member.dto.response.JoinResponse;
import api.wantedpreonboardingbackend.domain.member.dto.response.LoginResponse;
import api.wantedpreonboardingbackend.domain.member.entity.Member;
import api.wantedpreonboardingbackend.domain.member.service.MemberService;
import api.wantedpreonboardingbackend.domain.member.dto.request.JoinRequest;
import api.wantedpreonboardingbackend.global.base.ResponseForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/member", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
@Tag(name = "ApiMemberController", description = "회원가입, 로그인 컨트롤러")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/")
    @Operation(summary = "회원 가입")
    public ResponseForm<JoinResponse> join(@Valid @RequestBody JoinRequest joinRequest) {
        Member member = memberService.join(joinRequest.getEmail(), joinRequest.getPassword());
        if (member == null) {
            return ResponseForm.of("F-001", "이미 존재하는 이메일");
        }
        return ResponseForm.of("S-001", "회원 가입 성공", JoinResponse.of(member));
    }

    @PostMapping("/login")
    @Operation(summary = "로그인, 토큰 발급")
    public ResponseForm<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        Member member = memberService.findByEmail(loginRequest.getEmail());
        if (member == null) {
            return ResponseForm.of("F-001", "등록되지 않은 회원");
        }
        String jwtToken = memberService.generateJwtToken(member, loginRequest.getPassword());
        if (jwtToken == null) {
            return ResponseForm.of("F-002", "비밀번호 불일치");
        }
        return ResponseForm.of("S-002", "로그인 성공", LoginResponse.of(jwtToken));
    }
}
