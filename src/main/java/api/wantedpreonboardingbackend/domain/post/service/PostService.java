package api.wantedpreonboardingbackend.domain.post.service;

import api.wantedpreonboardingbackend.domain.post.entity.Post;
import api.wantedpreonboardingbackend.domain.post.repository.PostRepository;
import api.wantedpreonboardingbackend.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Post create(Member author, String title, String content) {
        return postRepository.save(Post.of(author, title, content));
    }
}
