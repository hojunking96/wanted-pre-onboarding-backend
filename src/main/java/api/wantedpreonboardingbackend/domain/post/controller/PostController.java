package api.wantedpreonboardingbackend.domain.post.controller;

import api.wantedpreonboardingbackend.domain.post.dto.request.CreateRequest;
import api.wantedpreonboardingbackend.domain.post.dto.request.ModifyRequest;
import api.wantedpreonboardingbackend.domain.post.dto.response.*;
import api.wantedpreonboardingbackend.domain.post.entity.Post;
import api.wantedpreonboardingbackend.domain.post.service.PostService;
import api.wantedpreonboardingbackend.domain.member.entity.Member;
import api.wantedpreonboardingbackend.domain.member.service.MemberService;
import api.wantedpreonboardingbackend.global.dto.CustomSuccessCode;
import api.wantedpreonboardingbackend.global.dto.ResponseForm;
import api.wantedpreonboardingbackend.global.dto.CustomFailureCode;
import api.wantedpreonboardingbackend.global.util.CustomUtility;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/posts", produces = APPLICATION_JSON_VALUE)
@Tag(name = "PostController", description = "게시글 컨트롤러")
public class PostController {

    private final PostService postService;
    private final MemberService memberService;

    @PostMapping(value = "", consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "게시글 생성", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ResponseForm<CreateResponse>> create(@AuthenticationPrincipal User user, @Valid @RequestBody CreateRequest createRequest) {
        Member member = memberService.findByEmail(user.getUsername());
        Post newPost = postService.create(member, createRequest.getTitle(), createRequest.getContent());
        return CustomUtility.sp.responseEntityOf(ResponseForm.of(CustomSuccessCode.S_201, CreateResponse.of(newPost)));
    }

    @GetMapping(value = "")
    @Operation(summary = "게시글 목록 조회")
    public ResponseEntity<ResponseForm<GetPostsResponse>> getPosts(@RequestParam(defaultValue = "0") int pageNumber,
                                                                   @RequestParam(defaultValue = "10") int pageSize) {
        List<Post> posts = postService.getPosts(pageNumber, pageSize);
        return CustomUtility.sp.responseEntityOf(ResponseForm.of(CustomSuccessCode.S_202, GetPostsResponse.of(posts)));
    }

    @GetMapping(value = "/{postId}")
    @Operation(summary = "게시글 ID로 게시글 조회")
    public ResponseEntity<ResponseForm<GetPostResponse>> getPost(@PathVariable Long postId) {
        Post post = postService.getPost(postId);
        if (post == null) {
            return CustomUtility.sp.responseEntityOf(ResponseForm.of(CustomFailureCode.F_201));
        }
        return CustomUtility.sp.responseEntityOf(ResponseForm.of(CustomSuccessCode.S_203, GetPostResponse.of(post)));
    }

    @PatchMapping(value = "/{postId}", consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "게시글 수정", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ResponseForm<ModifyResponse>> modify(@AuthenticationPrincipal User user,
                                                               @Valid @RequestBody ModifyRequest modifyRequest,
                                                               @PathVariable Long postId) {
        ResponseEntity<ResponseForm<Post>> validateResult = validate(user, postId);
        if (Objects.requireNonNull(validateResult.getBody()).isFail()) {
            return CustomUtility.sp.responseEntityOf(ResponseForm.of(validateResult.getBody().getResultCode(), validateResult.getBody().getMessage()));
        }
        Post post = validateResult.getBody().getData();
        Post modfiedPost = postService.modify(post, modifyRequest.getTitle(), modifyRequest.getContent());
        return CustomUtility.sp.responseEntityOf(ResponseForm.of(CustomSuccessCode.S_204, ModifyResponse.of(modfiedPost)));
    }

    @DeleteMapping(value = "/{postId}")
    @Operation(summary = "게시글 삭제", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ResponseForm<DeleteResponse>> delete(@AuthenticationPrincipal User user, @PathVariable Long postId) {
        ResponseEntity<ResponseForm<Post>> validateResult = validate(user, postId);
        if (Objects.requireNonNull(validateResult.getBody()).isFail()) {
            return CustomUtility.sp.responseEntityOf(ResponseForm.of(validateResult.getBody().getResultCode(), validateResult.getBody().getMessage()));
        }
        postService.delete(postId);
        return CustomUtility.sp.responseEntityOf(ResponseForm.of(CustomSuccessCode.S_205, DeleteResponse.of(postId)));
    }

    private ResponseEntity<ResponseForm<Post>> validate(User user, Long postId) {
        Member member = memberService.findByEmail(user.getUsername());
        if (member == null) {
            return CustomUtility.sp.responseEntityOf(ResponseForm.of(CustomFailureCode.F_102));
        }
        Post post = postService.getPost(postId);
        if (post == null) {
            return CustomUtility.sp.responseEntityOf(ResponseForm.of(CustomFailureCode.F_201));
        }
        if (!Objects.equals(post.getAuthor().getId(), member.getId())) {
            return CustomUtility.sp.responseEntityOf(ResponseForm.of(CustomFailureCode.F_202));
        }
        return CustomUtility.sp.responseEntityOf(ResponseForm.of(CustomSuccessCode.S_206, post));
    }
}
