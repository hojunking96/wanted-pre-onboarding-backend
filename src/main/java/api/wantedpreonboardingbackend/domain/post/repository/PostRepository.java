package api.wantedpreonboardingbackend.domain.post.repository;

import api.wantedpreonboardingbackend.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
