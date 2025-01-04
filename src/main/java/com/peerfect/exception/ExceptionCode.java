package com.peerfect.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {
    UNABLE_TO_SEND_EMAIL(500, "이메일 전송에 실패했습니다.");

    private final int status;
    private final String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
} 