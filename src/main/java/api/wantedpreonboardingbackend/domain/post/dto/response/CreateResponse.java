package api.wantedpreonboardingbackend.domain.post.dto.response;

import api.wantedpreonboardingbackend.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateResponse {
    private final Post post;

    public static CreateResponse of(Post post) {
        return new CreateResponse(post);
    }
}
