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
    private static String memberNickName;
    private static String memberAccessToken;
    //todo 토큰들 만료에 관한건 구현 아직 안함

    @GetMapping("/checkNickName")
    public ResponseEntity<?> checkNickName(@RequestBody String name){

        if(memberService.isNickNameExist(name))
            return ResponseEntity.ok(name + "중복된 닉네임입니다");
        else
            return ResponseEntity.ok(name + "중복되지 않은 닉네임입니다");
    }

    //이메일 인증을 하고나서 멤버가 회원인지 아닌지를 구분

    @PostMapping("/checkMember")
    public ResponseEntity<Map<String, Object>> checkMember(@RequestBody Map<String, String> userData) {
        Map<String, Object> response = new HashMap<>();

            //String memberName = userData.get("nickname");
            String memberEmail = userData.get("email");
            String authCode = userData.get("authCode");

            //String memberPassword = "password";
            //멤버가 있으니깐 userId, accessToken, nickName 전달하기

            if (memberService.isEmailExists(memberEmail)) {
                memberId = memberService.getMemberId(memberEmail);
                memberNickName = memberService.getMemberNickName(memberEmail);
                memberAccessToken = tokenService.getAccessToken(memberId);

                response.put("status", "success");
                response.put("message", "회원입니다.");
                response.put("memberId", memberId);
                response.put("nickName", memberNickName);
                response.put("accessToken",memberAccessToken);

                return ResponseEntity.ok(response);
            } else {
                response.put("message", "회원이 아닙니다");
                return ResponseEntity.ok(response);
            }


    }
    @PostMapping("/insertMember")
    public ResponseEntity<Map<String, Object>> insertMember(@RequestBody Map<String, String> userData) {
        Map<String, Object> response = new HashMap<>();

        String memberName = userData.get("nickname");
        String memberEmail = userData.get("email");
        boolean memberAgree = Boolean.parseBoolean(userData.get("agree"));

        MemberVO memberVO = new MemberVO(memberName,memberEmail, memberAgree);

        memberService.insertUser(memberVO);
        memberAccessToken = jwtTokenProvider.generateAccessToken(memberId);

        response.put("status", "success");
        response.put("message", "회원가입 완료.");
        response.put("nickname", memberName);
        response.put("memberId", memberId);
        response.put("accessToken",memberAccessToken);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }


    //todo 리프레시 확인

    /*
    @PostMapping("/regenerate-accesstoken")
    public ResponseEntity<?> generateAccessToken(){

    }


    // 토큰
    @PostMapping("/regenerate-refreshtoken")
    public ResponseEntity<?> generateAccessToken(){

    }
    */
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