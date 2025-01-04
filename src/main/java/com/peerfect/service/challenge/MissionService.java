package com.peerfect.service.challenge;

import com.peerfect.repository.challenge.MissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MissionService {



    public List<Map<String, String>> getUIMissionList(String challengeNo) {

        return MissionRepository.getUIMissionList(challengeNo);
    }

    public List<Map<String, String>> getUXMissionList(String challengeNo) {
        return MissionRepository.getUXMissionList(challengeNo);

    }
}

