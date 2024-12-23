package com.peerfect.vo.utils;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VerifyVO {
    private String emailAddress;
    private String emailAuth;
    private LocalDateTime emailTime;

    public VerifyVO(String emailAddress, String emailAuth, LocalDateTime emailTime) {
        this.emailAddress = emailAddress;
        this.emailAuth = emailAuth;
        this.emailTime = emailTime;
    }

    public VerifyVO() {

    }
}
