package api.wantedpreonboardingbackend.domain.post.dto.response;


import api.wantedpreonboardingbackend.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetPostsResponse {
    private List<Post> posts;

    public static GetPostsResponse of(List<Post> posts) {
        return new GetPostsResponse(posts);
    }
}
