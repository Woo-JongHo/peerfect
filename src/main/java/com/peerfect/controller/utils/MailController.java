package com.peerfect.controller.utils;

import com.peerfect.DTO.MailDTO;
import com.peerfect.service.utils.MailService;
import com.peerfect.vo.utils.EmailVO;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/email")
public class MailController {

    private final MailService mailService;

    @PostMapping("/emailCheck")
    public ResponseEntity<Map<String, Object>> emailCheck(@RequestBody MailDTO mailDTO) throws MessagingException, UnsupportedEncodingException {
        String email = mailDTO.getEmail(); // 프론트엔드에서 전달된 이메일 데이터
        log.info("Received email: {}", email);

        Map<String, Object> response = new HashMap<>();
        try {
            String authCode = mailService.sendSimpleMessage(email);
            response.put("status", "success");
            response.put("authCode", authCode);
            response.put("message", "인증 코드가 이메일로 발송되었습니다.");
            LocalDateTime time = LocalDateTime.now();            //이메일 디비에 저장

            EmailVO ev = new EmailVO(email, authCode, time);

            mailService.setEmailVerify(ev);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage());
            response.put("status", "error");
            response.put("message", "이메일 발송 중 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    //프론트에서 인증번호를 입력하면, emailDB와 확인 후,
    //맞으면 users Table 생성 틀리면 틀렸다고 메세지 보내기

    @PostMapping("/verifyCode")
    public ResponseEntity<Map<String, Object>> verifyCode(@RequestBody Map<String, String> request) {
        String email = request.get("email"); // 프론트엔드에서 전달된 이메일
        String authCode = request.get("authCode"); // 프론트엔드에서 입력한 인증 코드

        Map<String, Object> response = new HashMap<>();
        try {
            int re = mailService.getEmailVerify(email, authCode);
            if (re != 0){
                response.put("status", "success");
                response.put("message", "인증에 성공했습니다.");
            } else {
                response.put("status", "error");
                response.put("message", "인증 코드가 유효하지 않거나 일치하지 않습니다.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error during verification: {}", e.getMessage());
            response.put("status", "error");
            response.put("message", "서버에서 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }



}
