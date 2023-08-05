package api.wantedpreonboardingbackend.domain.post.entity;

import api.wantedpreonboardingbackend.domain.member.entity.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Post {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member author;
    @NotEmpty(message = "제목을 입력하세요")
    private String title;
    @NotEmpty(message = "내용을 입력하세요")
    private String content;

    public static Post of(Member author, String title, String content) {
        return Post.builder()
                .author(author)
                .title(title)
                .content(content)
                .build();
    }
}
