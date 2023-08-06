package api.wantedpreonboardingbackend.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomSuccessCode {
    S_101("S-101", "회원 가입 성공"),
    S_102("S-102", "로그인 성공"),
    S_201("S-201", "게시글 생성 성공"),
    S_202("S-202", "게시글 목록 조회 성공"),
    S_203("S-203", "게시글 조회 성공"),
    S_204("S-204", "게시글 수정 성공"),
    S_205("S-205", "게시글 삭제 성공");

    private final String code;
    private final String message;
}
