package com.peerfect.service.member;

import com.peerfect.DTO.MemberChallengeDTO;
import com.peerfect.repository.challenge.ChallengeRepository;
import com.peerfect.repository.member.MemberRepository;
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
            throw new IllegalArgumentException("존재하지 않는 회원 ID입니다: " + memberId);
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

        // 현재 챌린지 설정
        String currentChallenge = null;
        String currentDay = null;

        if (uiStart != null) {
            currentChallenge = "UI";
            currentDay = calculateChallengeDay(uiStart);
        } else if (uxStart != null) {
            currentChallenge = "UX";
            currentDay = calculateChallengeDay(uxStart);
        }

        // 6️⃣ 챌린지 정보 추가
        Map<String, Object> challengeInfo = new HashMap<>();
        if (currentChallenge != null) {
            challengeInfo.put("currentChallenge", currentChallenge);
            challengeInfo.put("currentDay", currentDay);
        } else {
            challengeInfo = null;
        }
        response.put("challengeInfo", challengeInfo);

        // 7️⃣ 정제된 데이터 반환
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
            return "day-1"; // 시작일이 미래일 경우 (예외 처리)
        }

        return "day-" + (daysBetween + 1); // 현재 진행 중인 day 값 반환 (day-1부터 시작)
    }
}
