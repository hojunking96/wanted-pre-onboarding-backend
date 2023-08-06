package api.wantedpreonboardingbackend.domain.post.dto.response;

import api.wantedpreonboardingbackend.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ModifyResponse {
    private Post post;

    public static ModifyResponse of(Post post) {
        return new ModifyResponse(post);
    }
}
