package api.wantedpreonboardingbackend.domain.member.repository;

import api.wantedpreonboardingbackend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
