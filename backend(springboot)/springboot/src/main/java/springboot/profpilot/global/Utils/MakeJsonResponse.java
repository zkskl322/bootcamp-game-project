package springboot.profpilot.global.Utils;

import java.util.HashMap;
import java.util.Map;
import java.lang.reflect.Field;


public class MakeJsonResponse {
    public static Map<String, String> makeJsonResponse(Object entity) {
        Map<String, String> response = new HashMap<>();

        try {
            Class<?> clazz = entity.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true); // private 필드에 접근할 수 있도록 설정
                Object value = field.get(entity);
                if (value != null) {
                    response.put(field.getName(), value.toString());
                } else {
                    response.put(field.getName(), "NONE");
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            // 예외 처리: 필요시 로그를 남기거나 특정 동작을 수행할 수 있습니다.
        }

        return response;
    }
}
