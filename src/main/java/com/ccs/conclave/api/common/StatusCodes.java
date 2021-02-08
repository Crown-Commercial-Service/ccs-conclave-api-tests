package com.ccs.conclave.api.common;

public enum StatusCodes {
    OK("200"),
    CREATED("201"),
    DUPLICATE_RESOURCE(""),
    BAD_REQUEST("400"),
    INTERNAL_SERVER_ERROR("500"),
    UPDATED("200"),
    NOT_FOUND("404");

    private String code;

    StatusCodes(String code) {
        this.code = code;
    }

    public int getCode() {
        return Integer.parseInt(code);
    }
}