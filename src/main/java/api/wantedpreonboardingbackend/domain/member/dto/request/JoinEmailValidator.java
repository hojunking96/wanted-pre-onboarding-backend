package api.wantedpreonboardingbackend.domain.member.dto.request;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class JoinEmailValidator implements ConstraintValidator<ValidEmail, String> {

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null) {
            return false;
        }
        return isValidEmail(email);
    }

    private boolean isValidEmail(String email) {
        return email.contains("@");
    }
}
