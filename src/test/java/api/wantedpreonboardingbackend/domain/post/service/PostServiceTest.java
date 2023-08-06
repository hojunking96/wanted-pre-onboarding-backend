package api.wantedpreonboardingbackend.domain.post.service;

import api.wantedpreonboardingbackend.domain.member.entity.Member;
import api.wantedpreonboardingbackend.domain.member.service.MemberService;
import api.wantedpreonboardingbackend.domain.post.entity.Post;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.MethodName.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PostServiceTest {

    @Autowired
    private PostService postService;
    @Autowired
    private MemberService memberService;
    private Member member;
    private String title;
    private String content;

    @BeforeEach
    void init() {
        String email = "user1@test.com";
        String password = "12345678";
        member = memberService.join(email, password);
        title = "testTitle1";
        content = "testContent1";
    }

    @Test
    @DisplayName("create 성공 테스트")
    void createSuccessTest() {
        // When
        Post post = postService.create(member, title, content);

        // Then
        assertThat(post.getId()).isEqualTo(1L);
        assertThat(post.getContent()).isEqualTo(content);
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getAuthor().getId()).isEqualTo(member.getId());
    }

    @Test
    @DisplayName("getPosts 성공 테스트")
    void getPostsSuccessTest() {
        // Given
        postService.create(member, "testTitle1", "testContent1");
        postService.create(member, "testTitle2", "testContent2");

        // When
        List<Post> posts = postService.getPosts(0, 10);

        // Then
        assertThat(posts.size()).isEqualTo(2);
        assertThat(posts.get(0).getId()).isEqualTo(2);
        assertThat(posts.get(1).getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("getPost 성공 테스트")
    void getPostSuccessTest() {
        // Given
        postService.create(member, title, content);
        Post testPost2 = postService.create(member, "testTitle2", "testContent2");

        // When
        Post post = postService.getPost(2L);

        // Then
        assertThat(post.getId()).isEqualTo(2L);
        assertThat(post.getTitle()).isEqualTo(testPost2.getTitle());
        assertThat(post.getContent()).isEqualTo(testPost2.getContent());
    }

    @Test
    @DisplayName("modify 성공 테스트")
    void modifySuccessTest() {
        // Given
        postService.create(member, title, content);
        Post testPost2 = postService.create(member, "testTitle2", "testContent2");

        // When
        Post post = postService.modify(testPost2, "newTitle", "newContent");

        // Then
        assertThat(post.getId()).isEqualTo(2L);
        assertThat(post.getTitle()).isEqualTo("newTitle");
        assertThat(post.getContent()).isEqualTo("newContent");
    }

    @Test
    @DisplayName("delete 성공 테스트")
    void deleteSuccessTest() {
        // Given
        postService.create(member, title, content);
        postService.create(member, "testTitle2", "testContent2");

        // When
        postService.delete(1L);

        // Then
        List<Post> posts = postService.getPosts(0, 10);
        assertThat(posts.size()).isEqualTo(1L);
        assertThat(posts.get(0).getTitle()).isEqualTo("testTitle2");
        assertThat(posts.get(0).getContent()).isEqualTo("testContent2");
    }
}