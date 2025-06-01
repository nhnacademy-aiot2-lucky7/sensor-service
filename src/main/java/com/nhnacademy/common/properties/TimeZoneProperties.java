package com.nhnacademy.common.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Set;

@Slf4j
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app")
public class TimeZoneProperties {

    private static final String DEFAULT_TIME_ZONE = "Asia/Seoul";

    private String timezone = DEFAULT_TIME_ZONE;

    public ZoneId getZoneId() {
        Set<String> validZoneIds = ZoneId.getAvailableZoneIds();

        String effectiveTimeZone;
        if (validZoneIds.contains(timezone)) {
            effectiveTimeZone = timezone;
        } else {
            log.warn("Invalid failed zone id '{}', falling back to default: {}", timezone, DEFAULT_TIME_ZONE);
            effectiveTimeZone = DEFAULT_TIME_ZONE;
        }

        return ZoneId.of(effectiveTimeZone);
    }

    public String getRawTimezone() {
        return timezone;
    }
}
