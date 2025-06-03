package com.nhnacademy.common.exception.http.extend;

import com.nhnacademy.common.exception.http.NotFoundException;

public class ThresholdHistoryNotFoundException extends NotFoundException {

    /// TODO:
    public ThresholdHistoryNotFoundException() {
        super("thresholdHistory is not found");
    }

    public ThresholdHistoryNotFoundException(long thresholdHistoryNo) {
        this(thresholdHistoryNo, null);
    }

    public ThresholdHistoryNotFoundException(long thresholdHistoryNo, Throwable cause) {
        super("thresholdHistory is not found: %d".formatted(thresholdHistoryNo), cause);
    }
}
