package com.nhnacademy.common.context;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;

@Slf4j
public class TimeZoneContext {

    @Getter
    private static ZoneId zoneId = ZoneId.of("Asia/Seoul");

    private static boolean initialized = false;

    private TimeZoneContext() {
        throw new IllegalStateException("Utility class");
    }

    public static synchronized void setZoneId(ZoneId newZoneId) {
        if (initialized) {
            log.warn("TimeZoneContext already initialized, ignoring setZoneId call.");
            return;
        }
        zoneId = newZoneId;
        initialized = true;
    }
}
