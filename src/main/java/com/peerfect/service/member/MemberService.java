package com.peerfect.service.member;

import com.peerfect.DTO.MemberChallengeDTO;
import com.peerfect.repository.challenge.ChallengeRepository;
import com.peerfect.repository.member.MemberRepository;
import com.peerfect.util.JwtTokenProvider;
import com.peerfect.vo.member.MemberVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final ChallengeRepository challengeRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public void insertUser(MemberVO userVO) {
        memberRepository.insertUser(userVO);
    }

    public boolean isEmailExists(String email) {
        return memberRepository.isMailExists(email);
    }

    public boolean authenticate(String email) {
        return memberRepository.authenticate(email);
    }

    public List<MemberChallengeDTO> getMemberMain(String memberId) {
        return memberRepository.getMemberMain(memberId);
    }

    public List<MemberChallengeDTO> getMemberNext(String memberId) {
        return memberRepository.getMemberNext(memberId);
    }

    public List<MemberChallengeDTO> getMemberComplete(String memberId) {
        return memberRepository.getMemberComplete(memberId);
    }

    public boolean isNickNameExist(String nickname) {
        return memberRepository.isNickNameExist(nickname);
    }

    public String getMemberId(String email) {
        return memberRepository.getMemberId(email);
    }

    public String getMemberNickName(String email) {
        return memberRepository.getMemberNickName(email);
    }

    public void deleteMember(String memberId) {
        memberRepository.deleteById(memberId);
    }

    public Map<String, Object> getMemberInfo(String memberId) {
        //  DB에서 원본 데이터 가져오기
        Map<String, Object> memberData = memberRepository.getMemberInfo(memberId);

        // 회원이 존재하지 않으면 예외 처리
        if (memberData == null || memberData.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "존재하지 않는 회원 ID입니다.");
            return errorResponse;
        }

        //  응답 데이터 정리
        Map<String, Object> response = new HashMap<>();
        response.put("memberId", memberData.get("member_id"));
        response.put("nickName", memberData.getOrDefault("member_name", "Unknown"));
        response.put("memberImg", memberData.getOrDefault("member_img", ""));
        response.put("memberEmail", memberData.getOrDefault("member_email", ""));

        //  Timestamp → LocalDateTime 변환
        LocalDateTime uiStart = convertToLocalDateTime(memberData.get("member_uistart"));
        LocalDateTime uxStart = convertToLocalDateTime(memberData.get("member_uxstart"));

        // 현재 챌린지 설정member_img
        String currentChallenge = null;
        String currentDay = null;

        if (uiStart != null) {
            currentChallenge = "UI";
            currentDay = calculateChallengeDay(uiStart);
        } else if (uxStart != null) {
            currentChallenge = "UX";
            currentDay = calculateChallengeDay(uxStart);
        }

        Map<String, Object> challengeInfo = new HashMap<>();
        if (currentChallenge != null) {
            challengeInfo.put("currentChallenge", currentChallenge);
            challengeInfo.put("currentDay", currentDay);
            challengeInfo.put("challengeNo", memberData.get("challenge_no"));
        } else {
            challengeInfo = null;
        }
        response.put("challengeInfo", challengeInfo);

        return response;
    }


    private LocalDateTime convertToLocalDateTime(Object timestampObj) {
        if (timestampObj instanceof Timestamp) {
            return ((Timestamp) timestampObj).toLocalDateTime();
        }
        return null; // 값이 없을 경우 null 반환
    }

    private String calculateChallengeDay(LocalDateTime startDate) {
        if (startDate == null) {
            return null; // 챌린지가 시작되지 않은 경우
        }

        LocalDateTime now = LocalDateTime.now(); // 현재 날짜
        long daysBetween = ChronoUnit.DAYS.between(startDate, now); // 시작일부터 현재일까지 일수 계산

        if (daysBetween < 0) {
            return "1"; // 시작일이 미래일 경우 (예외 처리)
        }

        return String.valueOf(daysBetween + 1); // day 값을 String으로 변환하여 반환
    }
    public HashMap<String, Object> startMemberChallenge(String memberId, String challengeNo) {

        return memberRepository.startMemberChallenge(memberId,challengeNo);
    }
}
