package com.peerfect.controller.challenge;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/challenge")
public class ChallengeController {

    @GetMapping("/")
    public String getChallenge() {
        return "추후 개발 예정";
    }


    //todo challenge에 따른 missionList 구현
    @GetMapping("/{challengeNo}/ui-missionlist")
    public ResponseEntity<?> getUIMissionList(@PathVariable String challengeNo){
        return ResponseEntity.ok("");
    }

    @GetMapping("/{challengeNo}/ux-missionlist")
    public ResponseEntity<?> getUXMissionList(@PathVariable String challengeNo){
        return ResponseEntity.ok("");
    }
}
