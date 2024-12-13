package com.peerfect.controller.users;

import com.peerfect.service.utils.MailService;
import com.peerfect.service.users.UsersService;

import com.peerfect.vo.users.UsersVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UsersController {
    private final MailService mailService;
    private final UsersService usersService;


    @PostMapping("/insertUser")
    public ResponseEntity<Map<String, Object>> insertUser(@RequestBody Map<String, String> userData) {
        Map<String, Object> response = new HashMap<>();

        try {
            // 프론트엔드에서 전달된 데이터 가져오기
            String name = userData.get("name");
            String email = userData.get("email");
            String password = userData.get("password");

            log.info("Received data - Name: {}, Email: {}", name, email);

            // 사용자 데이터 VO 생성
            UsersVO userVO = new UsersVO(name, email, password);

            // 사용자 정보 저장
            usersService.insertUser(userVO);
            response.put("status", "success");
            response.put("message", "사용자가 성공적으로 생성되었습니다.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error inserting user: {}", e.getMessage());
            response.put("status", "error");
            response.put("message", "사용자 생성 중 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
