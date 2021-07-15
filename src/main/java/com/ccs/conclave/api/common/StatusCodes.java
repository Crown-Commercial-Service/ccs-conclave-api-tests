package com.ccs.conclave.api.common;

public enum StatusCodes {
    OK("200"),
    CREATED("201"),
    DUPLICATE_RESOURCE("405"),
    BAD_REQUEST("400"),
    INTERNAL_SERVER_ERROR("500"),
    NOT_FOUND("404");

    private String code;

    StatusCodes(String code) {
        this.code = code;
    }

    public int getCode() {
        return Integer.parseInt(code);
    }
}
