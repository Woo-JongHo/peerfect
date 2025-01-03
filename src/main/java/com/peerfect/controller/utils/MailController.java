package com.peerfect.controller.utils;

import com.peerfect.DTO.MailDTO;
import com.peerfect.service.utils.MailService;
import com.peerfect.vo.utils.VerifyVO;
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

    //todo time이 5분이상 지나면 저절로 삭제되는 method
    //todo 같은 email이 들어왔을 때 체크하고 전 이메일을 삭제

    @PostMapping("/emailCheck")
    public ResponseEntity<Map<String, Object>> emailCheck(@RequestBody MailDTO mailDTO) throws MessagingException, UnsupportedEncodingException {
        String email = mailDTO.getEmail();
        log.info("Received email: {}", email);

        Map<String, Object> response = new HashMap<>();
        try {
            //mailService.deletePreviousVerifyByEmail(email); // 중복 이메일 처리
            String authCode = mailService.sendSimpleMessage(email);

            VerifyVO v = new VerifyVO(email, authCode, LocalDateTime.now());
            mailService.setEmailVerify(v);

            response.put("status", "success");
            response.put("message", "인증 코드가 이메일로 발송되었습니다.");
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
        String email = request.get("email");
        String authCode = request.get("authCode");

        Map<String, Object> response = new HashMap<>();
        try {
            int re = mailService.getEmailVerify(email, authCode);

            if (re != 0) {
                //mailService.deleteEmailVerify(email); // 인증 성공 시 데이터 삭제
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
