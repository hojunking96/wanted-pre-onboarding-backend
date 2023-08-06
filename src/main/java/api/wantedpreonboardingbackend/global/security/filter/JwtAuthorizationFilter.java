package api.wantedpreonboardingbackend.global.security.filter;

import api.wantedpreonboardingbackend.domain.member.entity.Member;
import api.wantedpreonboardingbackend.domain.member.service.MemberService;
import api.wantedpreonboardingbackend.global.dto.CustomFailureCode;
import api.wantedpreonboardingbackend.global.dto.ResponseForm;
import api.wantedpreonboardingbackend.global.jwt.JwtProvider;
import api.wantedpreonboardingbackend.global.util.CustomUtility;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final MemberService memberService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null) {
            String token = bearerToken.substring("Bearer ".length());
            if (jwtProvider.verify(token)) {
                Map<String, Object> claims = jwtProvider.getClaims(token);
                long id = (int) claims.get("id");
                Member member = memberService.findById(id).orElse(null);
                if (member == null) {
                    response.setStatus(HttpStatus.BAD_REQUEST.value());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(Objects.requireNonNull(CustomUtility.json.toStr(ResponseForm.of(CustomFailureCode.F_002))));
                    return;
                }
                forceAuthentication(member);
            }
        }
        filterChain.doFilter(request, response);
    }

    private void forceAuthentication(Member member) {
        User user = new User(member.getEmail(), member.getPassword(), member.getAuthorities());

        UsernamePasswordAuthenticationToken authentication =
                UsernamePasswordAuthenticationToken.authenticated(
                        user,
                        null,
                        member.getAuthorities()
                );

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }
}