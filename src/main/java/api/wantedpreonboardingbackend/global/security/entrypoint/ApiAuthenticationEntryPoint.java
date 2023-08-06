package api.wantedpreonboardingbackend.global.security.entrypoint;

import api.wantedpreonboardingbackend.global.dto.CustomFailureCode;
import api.wantedpreonboardingbackend.global.dto.ResponseForm;
import api.wantedpreonboardingbackend.global.util.CustomUtility;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Component
public class ApiAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(403);
        response.getWriter().append(CustomUtility.json.toStr(ResponseForm.of(CustomFailureCode.F_002)));
    }
}
