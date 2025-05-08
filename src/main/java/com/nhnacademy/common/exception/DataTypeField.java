package com.nhnacademy.common.exception;

public enum DataTypeField {

    EN_NAME("dataTypeEnName"),

    KR_NAME("dataTypeKrName");

    private final String type;

    DataTypeField(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
