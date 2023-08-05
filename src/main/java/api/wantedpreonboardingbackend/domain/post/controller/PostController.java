package api.wantedpreonboardingbackend.domain.post.controller;

import api.wantedpreonboardingbackend.domain.post.dto.request.CreateRequest;
import api.wantedpreonboardingbackend.domain.post.dto.response.CreateResponse;
import api.wantedpreonboardingbackend.domain.post.entity.Post;
import api.wantedpreonboardingbackend.domain.post.service.PostService;
import api.wantedpreonboardingbackend.domain.member.entity.Member;
import api.wantedpreonboardingbackend.domain.member.service.MemberService;
import api.wantedpreonboardingbackend.global.base.ResponseForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/post", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
@Tag(name = "PostController", description = "게시글 컨트롤러")
public class PostController {

    private final PostService postService;
    private final MemberService memberService;

    @PostMapping(value = "/")
    @Operation(summary = "게시글 생성", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseForm<CreateResponse> create(@AuthenticationPrincipal User user, @Valid @RequestBody CreateRequest createRequest) {
        if (user == null) {
            return ResponseForm.of("F-101", "인증되지 않은 사용자");
        }
        Member member = memberService.findByEmail(user.getUsername());
        Post newPost = postService.create(member, createRequest.getTitle(), createRequest.getContent());
        return ResponseForm.of("S-101", "게시글 생성 성공", CreateResponse.of(newPost));
    }
}
