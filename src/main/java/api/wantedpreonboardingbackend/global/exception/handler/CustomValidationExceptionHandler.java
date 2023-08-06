package api.wantedpreonboardingbackend.global.exception.handler;

import api.wantedpreonboardingbackend.global.dto.ResponseForm;
import api.wantedpreonboardingbackend.global.dto.CustomErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
public class CustomValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseForm<String> handleValidationException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        List<ObjectError> errors = bindingResult.getAllErrors();
        for (ObjectError error : errors) {
            if (error instanceof FieldError fieldError) {
                String fieldName = fieldError.getField();

                if (fieldName.equals("email")) {
                    return ResponseForm.of(CustomErrorCode.F_105);
                } else if (fieldName.equals("password")) {
                    return ResponseForm.of(CustomErrorCode.F_106);
                } else {
                    return ResponseForm.of(CustomErrorCode.F_107);
                }
            }
        }
        return ResponseForm.of(CustomErrorCode.F_107);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseForm<String> handleInvalidJsonException() {
        return ResponseForm.of(CustomErrorCode.F_107);
    }
}