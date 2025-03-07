package com.peerfect.vo.member;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CompleteVO {
    private int completeNo;
    private int challengeNo;
    private String completeUrl;
    private UUID memberId;
    private LocalDateTime completeTime;

}
