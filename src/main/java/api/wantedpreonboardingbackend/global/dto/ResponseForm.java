package api.wantedpreonboardingbackend.global.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ResponseForm<T> {
    private String resultCode;
    private String message;
    private T data;

    public static <T> ResponseForm<T> of(String resultCode, String message, T data) {
        return new ResponseForm<>(resultCode, message, data);
    }

    public static <T> ResponseForm<T> of(String resultCode, String message) {
        return of(resultCode, message, null);
    }

    public static <T> ResponseForm<T> of(CustomErrorCode customErrorCode) {
        return of(customErrorCode.getCode(), customErrorCode.getMessage());
    }

    public static <T> ResponseForm<T> of(CustomSuccessCode customSuccessCode, T data) {
        return of(customSuccessCode.getCode(), customSuccessCode.getMessage(), data);
    }

    @JsonIgnore
    public boolean isFail() {
        return resultCode.startsWith("F-");
    }
}
