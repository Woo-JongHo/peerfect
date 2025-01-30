package com.peerfect.service.challenge;

import com.peerfect.DTO.ChallengeDetailDTO;
import com.peerfect.DTO.PreviewDTO;
import com.peerfect.repository.challenge.ChallengeRepository;
import com.peerfect.repository.member.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChallengeService{



    @Transactional
    public String startMemberChallenge(String memberId, String challengeNo) {
        // 1. challenge_no로 challenge_type 조회
        String challengeType = ChallengeRepository.getChallengeTypeByNo(challengeNo);

        log.info("challengeType" + challengeType);

        if (challengeType == null) {
            throw new IllegalArgumentException("해당 챌린지가 존재하지 않습니다: " + challengeNo);
        }


        // 2. challenge_type에 따라 member 테이블의 특정 컬럼 업데이트
        if ("UI".equalsIgnoreCase(challengeType)) {
            MemberRepository.updateUIStart(memberId,challengeNo);
        } else if ("UX".equalsIgnoreCase(challengeType)) {
            MemberRepository.updateUXStart(memberId,challengeNo);

        } else {
            throw new IllegalArgumentException("지원되지 않는 챌린지 타입입니다: " + challengeType);
        }

        return "";
    }

    public List<PreviewDTO> getUIPreview(String preview) {
        return ChallengeRepository.getUIPreview(preview);
    }

    public List<PreviewDTO> getUXPreview(String preview) {
        return ChallengeRepository.getUXPreview(preview);
    }

    public ChallengeDetailDTO getChallengeDetail(String challengeTitle) {
        return ChallengeRepository.getChallengeDetail(challengeTitle);
    }
}

