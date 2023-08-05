package api.wantedpreonboardingbackend.domain.article.dto.response;

import api.wantedpreonboardingbackend.domain.article.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateResponse {
    private final Article article;

    public static CreateResponse of(Article article) {
        return new CreateResponse(article);
    }
}
