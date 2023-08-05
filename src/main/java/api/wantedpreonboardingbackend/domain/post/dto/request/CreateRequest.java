package api.wantedpreonboardingbackend.domain.post.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateRequest {
    @NotEmpty(message = "제목 공백")
    private String title;
    @NotEmpty(message = "내용 공백")
    private String content;
}
