package api.wantedpreonboardingbackend.domain.post.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteResponse {
    private Long postId;

    public static DeleteResponse of(Long postId) {
        return new DeleteResponse(postId);
    }
}
