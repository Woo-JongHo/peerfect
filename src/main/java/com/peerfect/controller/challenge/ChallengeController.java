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
@RequestMapping("/api")
public class ChallengeController {

    /*Challenge - Mission으로 연결해놓았고 현재는 Mission만 만드는 상태*/

    @GetMapping("/challenge")
    public String getChallenge() {
        return "추후 개발 예정";
    }


    /*-------------------------------------Mission Controller ---------------------*/


    @GetMapping("/mission")
    public String mission() {
        log.info("GET /api/mission 호출");
        return "Mission API 호출됨";
    }

    @GetMapping("/mission/{challengeNo}/ui-missionlist")
    public ResponseEntity<?> getUIMissionList(@PathVariable String challengeNo){




        return ResponseEntity.ok("");
    }

    @GetMapping("/mission/{challengeNo}/ux-missionlist")
    public ResponseEntity<?> getUXMissionList(@PathVariable String challengeNo){




        return ResponseEntity.ok("");
    }
}
