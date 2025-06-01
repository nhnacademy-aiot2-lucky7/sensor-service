package com.nhnacademy.common.config;

import com.nhnacademy.common.context.TimeZoneContext;
import com.nhnacademy.common.properties.TimeZoneProperties;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class TimeZoneInitializer {

    private final TimeZoneProperties properties;

    private boolean initialized = false;

    public TimeZoneInitializer(TimeZoneProperties properties) {
        this.properties = properties;
    }

    @PostConstruct
    public void init() {
        if (initialized) {
            return;
        }
        TimeZoneContext.setZoneId(properties.getZoneId());
        initialized = true;
    }
}
