package api.wantedpreonboardingbackend.global.exception.handler;

import api.wantedpreonboardingbackend.global.base.ResponseForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ResponseForm<String>> handleValidationException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        List<ObjectError> errors = bindingResult.getAllErrors();
        for (ObjectError error : errors) {
            if (error instanceof FieldError fieldError) {
                String fieldName = fieldError.getField();
                String errorMessage = fieldError.getDefaultMessage();

                if (fieldName.equals("email")) {
                    return new ResponseEntity<>(ResponseForm.of("F-201", errorMessage), HttpStatus.BAD_REQUEST);
                } else if (fieldName.equals("password")) {
                    return new ResponseEntity<>(ResponseForm.of("F-202", errorMessage), HttpStatus.BAD_REQUEST);
                }
            }
        }
        return new ResponseEntity<>(ResponseForm.of("F-200", "유효성 검사에 실패했습니다."), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ResponseForm<String>> handleInvalidJsonException() {
        return new ResponseEntity<>(ResponseForm.of("F-100", "JSON 입력 형식 오류"), HttpStatus.BAD_REQUEST);
    }
}