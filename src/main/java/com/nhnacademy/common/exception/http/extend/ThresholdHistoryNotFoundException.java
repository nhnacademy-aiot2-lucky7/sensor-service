package com.nhnacademy.common.exception.http.extend;

import com.nhnacademy.common.exception.http.NotFoundException;

public class ThresholdHistoryNotFoundException extends NotFoundException {

    public ThresholdHistoryNotFoundException(long thresholdHistoryNo) {
        this(thresholdHistoryNo, null);
    }

    public ThresholdHistoryNotFoundException(long thresholdHistoryNo, Throwable cause) {
        super("thresholdHistory is not found: %d".formatted(thresholdHistoryNo), cause);
    }
}
