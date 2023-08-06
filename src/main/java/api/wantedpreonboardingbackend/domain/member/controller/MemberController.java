package api.wantedpreonboardingbackend.domain.member.controller;

import api.wantedpreonboardingbackend.domain.member.dto.request.LoginRequest;
import api.wantedpreonboardingbackend.domain.member.dto.response.JoinResponse;
import api.wantedpreonboardingbackend.domain.member.dto.response.LoginResponse;
import api.wantedpreonboardingbackend.domain.member.entity.Member;
import api.wantedpreonboardingbackend.domain.member.service.MemberService;
import api.wantedpreonboardingbackend.domain.member.dto.request.JoinRequest;
import api.wantedpreonboardingbackend.global.dto.CustomSuccessCode;
import api.wantedpreonboardingbackend.global.dto.ResponseForm;
import api.wantedpreonboardingbackend.global.dto.CustomFailureCode;
import api.wantedpreonboardingbackend.global.util.CustomUtility;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/member", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
@Tag(name = "ApiMemberController", description = "회원가입, 로그인 컨트롤러")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("")
    @Operation(summary = "회원 가입")
    public ResponseEntity<ResponseForm<JoinResponse>> join(@Valid @RequestBody JoinRequest joinRequest) {
        Member member = memberService.join(joinRequest.getEmail(), joinRequest.getPassword());
        if (member == null) {
            return CustomUtility.sp.responseEntityOf(ResponseForm.of(CustomFailureCode.F_101));
        }
        return CustomUtility.sp.responseEntityOf(ResponseForm.of(CustomSuccessCode.S_101, JoinResponse.of(member)));
    }

    @PostMapping("/login")
    @Operation(summary = "로그인, 토큰 발급")
    public ResponseEntity<ResponseForm<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        Member member = memberService.findByEmail(loginRequest.getEmail());
        if (member == null) {
            return CustomUtility.sp.responseEntityOf(ResponseForm.of(CustomFailureCode.F_102));
        }
        String jwtToken = memberService.generateJwtToken(member, loginRequest.getPassword());
        if (jwtToken == null) {
            return CustomUtility.sp.responseEntityOf(ResponseForm.of(CustomFailureCode.F_103));
        }
        return CustomUtility.sp.responseEntityOf(ResponseForm.of(CustomSuccessCode.S_102, LoginResponse.of(jwtToken)));
    }
}
