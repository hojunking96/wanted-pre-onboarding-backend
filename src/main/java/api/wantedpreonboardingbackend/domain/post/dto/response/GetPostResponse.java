package api.wantedpreonboardingbackend.domain.post.dto.response;


import api.wantedpreonboardingbackend.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetPostResponse {
    private Post post;

    public static GetPostResponse of(Post post) {
        return new GetPostResponse(post);
    }
}
