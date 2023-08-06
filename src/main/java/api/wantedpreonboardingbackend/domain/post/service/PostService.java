package api.wantedpreonboardingbackend.domain.post.service;

import api.wantedpreonboardingbackend.domain.post.entity.Post;
import api.wantedpreonboardingbackend.domain.post.repository.PostRepository;
import api.wantedpreonboardingbackend.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public Post create(Member author, String title, String content) {
        return postRepository.save(Post.of(author, title, content));
    }

    @Transactional(readOnly = true)
    public List<Post> getPosts(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Post> page = postRepository.findAllByOrderByIdDesc(pageable);
        return page.getContent();
    }

    @Transactional(readOnly = true)
    public Post getPost(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    @Transactional
    public Post modify(Post post, String newTitle, String newContent) {
        post.modify(newTitle, newContent);
        return post;
    }
}
