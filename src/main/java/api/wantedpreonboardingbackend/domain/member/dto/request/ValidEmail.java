package api.wantedpreonboardingbackend.domain.member.dto.request;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = JoinEmailValidator.class)
public @interface ValidEmail {
    String message() default "올바르지 않은 이메일 형식";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
