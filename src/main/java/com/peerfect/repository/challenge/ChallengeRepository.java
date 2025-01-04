package com.peerfect.repository.challenge;


import com.peerfect.DTO.ChallengeDetailDTO;
import com.peerfect.DTO.PreviewDTO;
import com.peerfect.db.challenge.ChallengeDBManger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ChallengeRepository {
    public static List<PreviewDTO> getUIPreview(String preview) {
        return ChallengeDBManger.getUIPreview(preview);
    }

    public static List<PreviewDTO> getUXPreview(String preview) {
        return ChallengeDBManger.getUXPreview(preview);
    }

    public static ChallengeDetailDTO getChallengeDetail(String challengeTitle) {
        return ChallengeDBManger.getChallengeDetail(challengeTitle);
    }
}
