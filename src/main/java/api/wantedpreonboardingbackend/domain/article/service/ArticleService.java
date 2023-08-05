package api.wantedpreonboardingbackend.domain.article.service;

import api.wantedpreonboardingbackend.domain.article.entity.Article;
import api.wantedpreonboardingbackend.domain.article.repository.ArticleRepository;
import api.wantedpreonboardingbackend.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public Article create(Member author, String title, String content) {
        return articleRepository.save(Article.of(author, title, content));
    }
}
