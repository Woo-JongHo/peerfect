package com.peerfect.vo.utils;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmailVO {
    private String emailAddress;
    private String emailAuth;
    private LocalDateTime emailTime;

    public EmailVO(String emailAddress, String emailAuth, LocalDateTime emailTime) {
        this.emailAddress = emailAddress;
        this.emailAuth = emailAuth;
        this.emailTime = emailTime;
    }

    public EmailVO() {

    }
}
