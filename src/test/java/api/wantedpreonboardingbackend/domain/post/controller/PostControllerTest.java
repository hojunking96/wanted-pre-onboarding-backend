package api.wantedpreonboardingbackend.domain.post.controller;

import api.wantedpreonboardingbackend.domain.member.entity.Member;
import api.wantedpreonboardingbackend.domain.member.service.MemberService;
import api.wantedpreonboardingbackend.domain.post.service.PostService;
import api.wantedpreonboardingbackend.global.dto.CustomFailureCode;
import api.wantedpreonboardingbackend.global.dto.CustomSuccessCode;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.MethodName.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PostControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private PostService postService;
    @Autowired
    private MemberService memberService;
    private Member member;

    @BeforeEach()
    void saveMember() {
        String email = "user1@test.com";
        String password = "12345678";
        member = memberService.join(email, password);
    }

    @Test
    @DisplayName("게시글 생성 성공 테스트")
    @WithMockUser(username = "user1@test.com", roles = "MEMBER")
    void createSuccessTest() throws Exception {
        // When
        ResultActions resultActions = mvc
                .perform(
                        post("/api/posts")
                                .content("""
                                        {
                                            "title": "testTitle",
                                            "content": "testContent"
                                        }
                                        """.stripIndent())
                                .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(PostController.class))
                .andExpect(handler().methodName("create"))
                .andExpect(jsonPath("$.resultCode").value(CustomSuccessCode.S_201.getCode()))
                .andExpect(jsonPath("$.message").value(CustomSuccessCode.S_201.getMessage()))
                .andExpect(jsonPath("$.data.post.id").value(1))
                .andExpect(jsonPath("$.data.post.title").value("testTitle"))
                .andExpect(jsonPath("$.data.post.content").value("testContent"))
                .andExpect(jsonPath("$.data.post.author").exists())
        ;
    }

    @Test
    @DisplayName("게시글 생성 실패 테스트 - 인증 오류")
    @WithMockUser(username = "user2@test.com", roles = "MEMBER")
    void createFailureTest() throws Exception {
        // When
        ResultActions resultActions = mvc
                .perform(
                        post("/api/posts")
                                .content("""
                                        {
                                            "title": "testTitle",
                                            "content": "testContent"
                                        }
                                        """.stripIndent())
                                .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is4xxClientError())
                .andExpect(handler().handlerType(PostController.class))
                .andExpect(handler().methodName("create"))
                .andExpect(jsonPath("$.resultCode").value(CustomFailureCode.F_102.getCode()))
                .andExpect(jsonPath("$.message").value(CustomFailureCode.F_102.getMessage()))
                .andExpect(jsonPath("$.data").isEmpty())
        ;
    }

    @Test
    @DisplayName("게시글 목록 조회 성공 테스트")
    void getPostsSuccessTest() throws Exception {
        // Given
        int pageNumber = 0;
        int pageSize = 10;

        // When
        ResultActions resultActions = mvc
                .perform(
                        get("/api/posts")
                                .param("pageNumber", String.valueOf(pageNumber))
                                .param("pageSize", String.valueOf(pageSize))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(PostController.class))
                .andExpect(handler().methodName("getPosts"))
                .andExpect(jsonPath("$.resultCode").value(CustomSuccessCode.S_202.getCode()))
                .andExpect(jsonPath("$.message").value(CustomSuccessCode.S_202.getMessage()))
                .andExpect(jsonPath("$.data.posts").exists())
        ;
    }

    @Test
    @DisplayName("게시글 ID 조회 성공 테스트")
    void getPostSuccessTest() throws Exception {
        // Given
        int postId = 1;
        postService.create(member, "title", "content");

        // When
        ResultActions resultActions = mvc
                .perform(
                        get("/api/posts/" + postId)
                                .param("postId", String.valueOf(postId))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(PostController.class))
                .andExpect(handler().methodName("getPost"))
                .andExpect(jsonPath("$.resultCode").value(CustomSuccessCode.S_203.getCode()))
                .andExpect(jsonPath("$.message").value(CustomSuccessCode.S_203.getMessage()))
                .andExpect(jsonPath("$.data.post").exists())
        ;
    }

    @Test
    @DisplayName("게시글 ID 조회 실패 테스트 - 유효하지 않은 게시물 ID")
    void getPostFailureTest() throws Exception {
        // Given
        int postId = 1;

        // When
        ResultActions resultActions = mvc
                .perform(
                        get("/api/posts/" + postId)
                                .param("postId", String.valueOf(postId))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is4xxClientError())
                .andExpect(handler().handlerType(PostController.class))
                .andExpect(handler().methodName("getPost"))
                .andExpect(jsonPath("$.resultCode").value(CustomFailureCode.F_201.getCode()))
                .andExpect(jsonPath("$.message").value(CustomFailureCode.F_201.getMessage()))
                .andExpect(jsonPath("$.data").isEmpty())
        ;
    }

    @Test
    @WithMockUser(username = "user1@test.com", roles = "MEMBER")
    @DisplayName("게시글 수정 성공 테스트")
    void modifySuccessTest() throws Exception {
        // Given
        int postId = 1;
        postService.create(member, "title", "content");

        // When
        ResultActions resultActions = mvc
                .perform(
                        patch("/api/posts/" + postId)
                                .content("""
                                        {
                                            "title": "updatedTitle",
                                            "content": "updatedContent"
                                        }
                                        """.stripIndent())
                                .param("postId", String.valueOf(postId))
                                .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(PostController.class))
                .andExpect(handler().methodName("modify"))
                .andExpect(jsonPath("$.resultCode").value(CustomSuccessCode.S_204.getCode()))
                .andExpect(jsonPath("$.message").value(CustomSuccessCode.S_204.getMessage()))
                .andExpect(jsonPath("$.data.post.title").value("updatedTitle"))
                .andExpect(jsonPath("$.data.post.content").value("updatedContent"))
        ;
    }

    @Test
    @WithMockUser(username = "user1@test.com", roles = "MEMBER")
    @DisplayName("게시글 수정 실패 테스트 - 작성자가 아닌 유저 수정 시도")
    void modifyFailureTest() throws Exception {
        // Given
        int postId = 1;
        String email2 = "user2@test.com";
        String password2 = "12345678";
        Member member2 = memberService.join(email2, password2);
        postService.create(member2, "title", "content");

        // When
        ResultActions resultActions = mvc
                .perform(
                        patch("/api/posts/" + postId)
                                .param("postId", String.valueOf(postId))
                                .content("""
                                        {
                                            "title": "updatedTitle",
                                            "content": "updatedContent"
                                        }
                                        """.stripIndent())
                                .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is4xxClientError())
                .andExpect(handler().handlerType(PostController.class))
                .andExpect(handler().methodName("modify"))
                .andExpect(jsonPath("$.resultCode").value(CustomFailureCode.F_202.getCode()))
                .andExpect(jsonPath("$.message").value(CustomFailureCode.F_202.getMessage()))
                .andExpect(jsonPath("$.data").isEmpty())
        ;
    }

    @Test
    @WithMockUser(username = "user1@test.com", roles = "MEMBER")
    @DisplayName("게시글 삭제 성공 테스트")
    void deleteSuccessTest() throws Exception {
        // Given
        int postId = 1;
        postService.create(member, "title", "content");

        // When
        ResultActions resultActions = mvc
                .perform(
                        delete("/api/posts/" + postId)
                                .param("postId", String.valueOf(postId))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(PostController.class))
                .andExpect(handler().methodName("delete"))
                .andExpect(jsonPath("$.resultCode").value(CustomSuccessCode.S_205.getCode()))
                .andExpect(jsonPath("$.message").value(CustomSuccessCode.S_205.getMessage()))
                .andExpect(jsonPath("$.data.postId").value(1))
        ;
    }

    @Test
    @WithMockUser(username = "user1@test.com", roles = "MEMBER")
    @DisplayName("게시글 삭제 실패 테스트")
    void deleteFailureTest() throws Exception {
        // Given
        int postId = 1;
        String email2 = "user2@test.com";
        String password2 = "12345678";
        Member member2 = memberService.join(email2, password2);
        postService.create(member2, "title", "content");

        // When
        ResultActions resultActions = mvc
                .perform(
                        delete("/api/posts/" + postId)
                                .param("postId", String.valueOf(postId))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is4xxClientError())
                .andExpect(handler().handlerType(PostController.class))
                .andExpect(handler().methodName("delete"))
                .andExpect(jsonPath("$.resultCode").value(CustomFailureCode.F_202.getCode()))
                .andExpect(jsonPath("$.message").value(CustomFailureCode.F_202.getMessage()))
                .andExpect(jsonPath("$.data").isEmpty())
        ;
    }
}