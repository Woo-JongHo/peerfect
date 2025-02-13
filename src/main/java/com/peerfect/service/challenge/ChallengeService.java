package com.peerfect.service.challenge;

import com.peerfect.DTO.ChallengeDetailDTO;
import com.peerfect.DTO.PreviewDTO;
import com.peerfect.repository.challenge.ChallengeRepository;
import com.peerfect.repository.member.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChallengeService{

    @Transactional
    public HashMap<String, Object> startMemberChallenge(String memberId, String challengeNo) {
        HashMap<String, Object> map = new HashMap<>();

        if (!MemberRepository.isMemberExists(memberId)) {
            log.info("여기로들어와야함");
            map.put("status", "fail");
            map.put("message", "해당 유저가 존재하지 않습니다: " + memberId);
            return map;
        }

        String challengeType = ChallengeRepository.getChallengeTypeByNo(challengeNo);
        map.put("status", "참여하기 완료");
        map.put("memberId", memberId);

        return map;
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

