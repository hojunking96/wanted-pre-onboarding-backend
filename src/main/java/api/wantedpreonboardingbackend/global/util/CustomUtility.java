package api.wantedpreonboardingbackend.global.util;

import api.wantedpreonboardingbackend.global.dto.ResponseForm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.LinkedHashMap;
import java.util.Map;

public class CustomUtility {
    public static class json {

        public static String toStr(Object object) {
            try {
                return new ObjectMapper().writeValueAsString(object);
            } catch (JsonProcessingException e) {
                return null;
            }
        }

        public static Map<String, Object> toMap(String jsonStr) {
            try {
                return new ObjectMapper().readValue(jsonStr, LinkedHashMap.class);
            } catch (JsonProcessingException e) {
                return null;
            }
        }
    }

    public static class sp {

        public static <T> ResponseEntity<ResponseForm<T>> responseEntityOf(ResponseForm<T> responseForm) {
            return responseEntityOf(responseForm, null);
        }

        public static <T> ResponseEntity<ResponseForm<T>> responseEntityOf(ResponseForm<T> responseForm, HttpHeaders headers) {
            return new ResponseEntity<>(responseForm, headers, responseForm.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
        }
    }
}