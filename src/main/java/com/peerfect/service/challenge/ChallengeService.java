package com.peerfect.service.challenge;

import com.peerfect.DTO.ChallengeDetailDTO;
import com.peerfect.DTO.PreviewDTO;
import com.peerfect.repository.challenge.ChallengeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChallengeService{



    public static String startMemberChallenge(String memberId, String challengeNo) {

        ChallengeRepository.startMemberChallenge(memberId,challengeNo);
        return "success";
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

