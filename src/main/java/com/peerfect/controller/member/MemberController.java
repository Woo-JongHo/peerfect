package com.peerfect.controller.member;

import com.peerfect.DTO.ChallengeDetailDTO;
import com.peerfect.DTO.MemberChallengeDTO;
import com.peerfect.DTO.PreviewDTO;
import com.peerfect.service.utils.MailService;
import com.peerfect.service.member.MemberService;
import com.peerfect.service.utils.TokenService;
import com.peerfect.util.JwtTokenProvider;
import com.peerfect.vo.member.MemberVO;
import com.peerfect.vo.utils.TokenVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {
    private final MailService mailService;
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenService tokenService;

    private static String memberId;

    //todo 토큰들 만료에 관한건 구현 아직 안함

    @GetMapping("/checkNickName")
    public ResponseEntity<?> checkNickName(@RequestBody String name){

        if(memberService.isNickNameExist(name))
            return ResponseEntity.ok(name + "중복된 닉네임입니다");
        else
            return ResponseEntity.ok(name + "중복되지 않은 닉네임입니다");

    }


    @PostMapping("/checkMember")
    public ResponseEntity<Map<String, Object>> insertUser(@RequestBody Map<String, String> userData) {
        Map<String, Object> response = new HashMap<>();

        try {
            String memberName = userData.get("nickname");
            String memberEmail = userData.get("email");
            String memberPassword = "password";

            log.info("Received data - Name: {}, Email: {}", memberName, memberEmail);

            if (memberService.isEmailExists(memberEmail)) {
                response.put("status", "success");
                response.put("message", "이미 회원가입이 되어있습니다.");

                response.put("next api", "api/member/login");


                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }

            MemberVO memberVO = new MemberVO(memberName, memberPassword,memberEmail);
            memberService.insertUser(memberVO);

            response.put("status", "success");
            response.put("message", "회원가입이 완료 되었습니다.");
            response.put("email", memberEmail);
            response.put("nickname", memberName);


            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error inserting user: {}", e.getMessage());
            response.put("status", "error");
            response.put("message", "사용자 생성 중 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("email");


        log.info(email);
        if (memberService.authenticate(email)) {

            memberId = memberService.getMemberId(email);

            log.info("memberId: {}" , memberId);

            String accessToken = jwtTokenProvider.generateAccessToken(memberId);

            //todo mebmerID에 맞는 refresh 토큰을 확인하고, 없으면 provider로 다시 제공
            String refreshToken = jwtTokenProvider.generateRefreshToken(memberId);

            TokenVO tokenVO = new TokenVO(UUID.fromString(memberId), accessToken, refreshToken, LocalDateTime.now().plusDays(7));
            tokenService.saveToken(tokenVO);

            Map<String, String> response = new HashMap<>();
            response.put("memberId", memberId);
            response.put("accessToken", accessToken);

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid credentials"));
        }
    }

    @GetMapping("/{memberId}/main")
    public ResponseEntity<List<MemberChallengeDTO>> getMemberMain(@PathVariable String memberId) {

        List<MemberChallengeDTO> list = memberService.getMemberMain(memberId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{memberId}/next")
    public ResponseEntity<List<MemberChallengeDTO>> getMemberNext(@PathVariable String memberId) {

        List<MemberChallengeDTO> list = memberService.getMemberNext(memberId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{memberId}/complete")
    public ResponseEntity<List<MemberChallengeDTO>> getChallengeComplete(@PathVariable String memberId) {

        List<MemberChallengeDTO> list = memberService.getMemberComplete(memberId);
        return ResponseEntity.ok(list);
    }


    @PutMapping("/{memberId}/challenges/{challengeNo}/start")
    public ResponseEntity<?> startChallenge(@PathVariable String memberId, String challengeNo){





        return ResponseEntity.ok("memberId + challengeNo로 어디에 했다를 알려줘야할듯");




    }
    //Start challenge

    @PutMapping("/{memberId}/challenges/{challengeNo}/stop")
    public ResponseEntity<?> stopChallenge(){
        return ResponseEntity.ok("");
    }
    //Stop challenge

    /*
    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refresh(@RequestBody Map<String, String> tokenData) {
        String refreshToken = tokenData.get("refreshToken");

        if (jwtTokenProvider.validateToken(refreshToken)) {
            String email = jwtTokenProvider.getMemberIdFromToken(refreshToken);

            TokenVO tokenVO = tokenService.getTokenByMemberId(email);
            if (tokenVO == null || !tokenVO.getRefreshToken().equals(refreshToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid or expired refresh token"));
            }

            String newAccessToken = jwtTokenProvider.generateAccessToken(email);
            String newRefreshToken = jwtTokenProvider.generateRefreshToken(email);
            tokenService.updateRefreshToken(email, newRefreshToken);

            Map<String, String> response = new HashMap<>();
            response.put("accessToken", newAccessToken);
            response.put("refreshToken", newRefreshToken);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid refresh token"));
    }*/


}