package com.peerfect.vo.utils;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TokenVO {
    private String memberId; // UUID 타입 사용
    private String accessToken;
    private String refreshToken;

    public TokenVO(String memberId, String accessToken, String refreshToken) {
        this.memberId = memberId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public TokenVO() {

    }
}
