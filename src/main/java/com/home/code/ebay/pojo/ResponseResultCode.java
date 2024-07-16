package com.home.code.ebay.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * API return status code
 */
@Getter
@AllArgsConstructor
public enum ResponseResultCode {

    SUCCESS(0, "success"),

    FAIL(1, "fail");

    private final Integer code;

    private final String message;
}
