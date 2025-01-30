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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
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
    private static String memberRefreshToken;
    //todo 토큰들 만료에 관한건 구현 아직 안함

    //01 멤버 info 환성
    //02 로그아웃 api
    //03 회원탈퇴 api
    //04 참여하기 api
    //04 토큰 재발급 api



    @GetMapping("/{memberId}/memberInfo")
    public ResponseEntity<?> memberInfo(){
        Map<String, Object> response = new HashMap<>();


        return ResponseEntity.ok(response);
    }

    // 멤버아이디, 토큰, 이미지,
    @PostMapping("/checkNickName")
    public ResponseEntity<?> checkNickName(@RequestBody Map<String, String> userData){

        String nickname = userData.get("nickname");
        if(memberService.isNickNameExist(nickname))
            return ResponseEntity.ok("중복된 닉네임입니다");
        else
            return ResponseEntity.ok("중복되지 않은 닉네임입니다");
    }

    //01 access token refresh 토큰을 timer를 이용해서 시간이 되면, 삭제하는 로직을 추가
    //02 accesstoken을 이용한 userInfo 를 가지고 오는 api 개발

    @PostMapping("/UserInfo")
    public ResponseEntity<?> userInfo(@RequestBody Map<String, String> userData){

        String accessToken = userData.get("access-token");
        String refreshToken = userData.get("refresh-token");

        return ResponseEntity.ok("");
    }

    //이메일 인증을 하고나서 멤버가 회원인지 아닌지를 구분
    @PostMapping("/checkMember")
    public ResponseEntity<Map<String, Object>> checkMember(@RequestBody Map<String, String> userData) {
        Map<String, Object> response = new HashMap<>();

            String memberEmail = userData.get("email");

            //멤버가 있으니깐 userId, accessToken, nickName 전달하기
            if (memberService.isEmailExists(memberEmail)) {
                memberId = memberService.getMemberId(memberEmail);
                memberNickName = memberService.getMemberNickName(memberEmail);

                memberAccessToken = tokenService.getAccessToken(memberId);
                memberRefreshToken = tokenService.getRefreshToken(memberId);

                response.put("status", "success");
                response.put("message", "회원입니다.");
                response.put("memberId", memberId);
                response.put("nickName", memberNickName);

                // HttpOnly 쿠키로 Refresh Token 설정
                ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", memberRefreshToken)
                        .httpOnly(true)              // HttpOnly 속성
                        .secure(true)                // HTTPS 환경에서만 사용 (로컬 환경에서는 false로 설정 가능)
                        .path("/")                   // 쿠키의 유효 경로
                        .maxAge(7 * 24 * 60 * 60)    // 쿠키 만료 시간 (7일)
                        .sameSite("Strict")          // CSRF 보호를 위한 SameSite 설정
                        .build();

                return ResponseEntity.ok()
                        .header("Authorization", "Bearer " + memberAccessToken)
                        .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                        .body(response);
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
        boolean memberRequired = Boolean.parseBoolean(userData.get("requiredterm"));
        boolean memberOptional = Boolean.parseBoolean(userData.get("optionalterm"));

        MemberVO memberVO = new MemberVO(memberName, memberEmail, memberRequired, memberOptional);

        // 사용자 저장 및 ID 가져오기
        memberService.insertUser(memberVO);
        String memberId = memberService.getMemberId(memberEmail);

        // Access Token 및 Refresh Token 생성
        String memberAccessToken = jwtTokenProvider.generateAccessToken(memberId);
        String memberRefreshToken = jwtTokenProvider.generateRefreshToken(memberId);

        TokenVO tokenVO = new TokenVO(UUID.fromString(memberId), memberAccessToken, memberRefreshToken);
        tokenService.saveToken(tokenVO);


        // 응답 데이터 설정
        response.put("status", "success");
        response.put("message", "회원가입 완료.");
        response.put("nickname", memberName);
        response.put("memberId", memberId);

        // HttpOnly 쿠키로 Refresh Token 설정
        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", memberRefreshToken)
                .httpOnly(true)              // HttpOnly 속성
                .secure(true)                // HTTPS 환경에서만 사용 (로컬 환경에서는 false로 설정 가능)
                .path("/")                   // 쿠키의 유효 경로
                .maxAge(7 * 24 * 60 * 60)    // 쿠키 만료 시간 (7일)
                .sameSite("Strict")          // CSRF 보호를 위한 SameSite 설정
                .build();

        // Access Token을 헤더에 추가 및 쿠키 추가
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + memberAccessToken)
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(response);
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

            TokenVO tokenVO = new TokenVO(UUID.fromString(memberId), accessToken, refreshToken);
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

    @DeleteMapping("/{memberId}/delete")
    public ResponseEntity<?> deleteMember(@PathVariable String memberId){

        log.info(memberId + " member Id ");

        memberService.deleteMember(memberId);

        return ResponseEntity.ok("delete member Successful");
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