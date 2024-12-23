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
@RequestMapping("/api/mission")
public class MissionController {


    //todo get missionList 구현
    @GetMapping("/mission/{challengeNo}/ui-missionlist")
    public ResponseEntity<?> getUIMissionList(@PathVariable String challengeNo){




        return ResponseEntity.ok("");
    }

    @GetMapping("/mission/{challengeNo}/ux-missionlist")
    public ResponseEntity<?> getUXMissionList(@PathVariable String challengeNo){




        return ResponseEntity.ok("");
    }
}
