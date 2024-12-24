package com.peerfect.vo.utils;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TokenVO {
    private UUID memberId; // UUID 타입 사용
    private String accessToken;
    private String refreshToken;
    private LocalDateTime expireToken;

    public TokenVO(UUID memberId, String accessToken, String refreshToken, LocalDateTime expireToken) {
        this.memberId = memberId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expireToken = expireToken;
    }

}
