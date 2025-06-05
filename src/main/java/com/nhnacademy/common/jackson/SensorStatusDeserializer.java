package com.nhnacademy.common.jackson;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.nhnacademy.sensor_type_mapping.domain.SensorStatus;

import java.io.IOException;

/**
 * Jackson 라이브러리용 커스텀 역직렬화기(deserializer)입니다.
 *
 * <p>JSON 데이터에서 {@code SensorStatus} 열거형(enum) 값을 변환할 때 사용됩니다.
 * 입력받은 문자열을 대소문자 구분 없이 {@link SensorStatus#from(String)} 메서드를 통해
 * 적절한 {@code SensorStatus} 값으로 변환합니다.</p>
 *
 * <p>이 클래스를 사용하면, JSON에서 "pending", "COMPLETED" 등과 같이
 * 다양한 케이스의 문자열을 {@code SensorStatus} enum 인스턴스로 안전하게 매핑할 수 있습니다.</p>
 *
 * <p>예외 발생 시 {@link com.nhnacademy.common.exception.http.BadRequestException}이
 * {@link SensorStatus#from(String)} 내부에서 던져질 수 있습니다.</p>
 *
 * <p>주로 DTO 클래스의 생성자 파라미터 또는 필드에
 * {@code @JsonDeserialize(using = SensorStatusDeserializer.class)} 어노테이션과 함께 사용됩니다.</p>
 *
 * @author Rayhke
 */
public class SensorStatusDeserializer extends JsonDeserializer<SensorStatus> {

    @Override
    public SensorStatus deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        String raw = p.getText();
        return SensorStatus.from(raw);
    }
}
