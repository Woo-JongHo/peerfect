package com.example.peerfect.vo;


import lombok.Data;

@Data
public class AdminVO {
    private int adminNo;
    private String adminId;
    private String adminName;
    private String adminEmail;

    public AdminVO(){}

    public AdminVO(int adminNo, String adminId, String adminName, String adminEmail) {
        this.adminNo = adminNo;
        this.adminId = adminId;
        this.adminName = adminName;
        this.adminEmail = adminEmail;
    }
}
