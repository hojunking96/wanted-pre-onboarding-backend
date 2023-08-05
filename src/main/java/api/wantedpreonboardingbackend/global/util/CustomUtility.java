package api.wantedpreonboardingbackend.global.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
}