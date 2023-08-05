package api.wantedpreonboardingbackend.global.base;

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
}
