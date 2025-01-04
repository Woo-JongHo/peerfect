package com.peerfect.repository.challenge;


import com.peerfect.db.challenge.MissionDBManger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MissionRepository {
    public static List<Map<String, String>> getUIMissionList(String challengeNo) {
        return MissionDBManger.getUIMissionList(challengeNo);
    }

    public static List<Map<String, String>> getUXMissionList(String challengeNo) {
        return MissionDBManger.getUXMissionList(challengeNo);

    }
}
