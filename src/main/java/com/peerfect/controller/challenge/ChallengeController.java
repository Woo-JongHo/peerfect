package com.peerfect.controller.challenge;

import com.peerfect.service.challenge.ChallengeService;
import com.peerfect.service.challenge.MissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/challenges")
public class ChallengeController {

    private final ChallengeService challengeService;

    @GetMapping("/")
    public String getChallenge() {
        return "추후 개발 예정";
    }


    @GetMapping("/ui-preview")
    public ResponseEntity<?> getUIPreview(){


        return ResponseEntity.ok("");
    }

    @GetMapping("/ux-preview")
    public ResponseEntity<?> getUXpreview(){

        return ResponseEntity.ok("");
    }
    @GetMapping("/{challengeNo}/detail")
    public ResponseEntity<?> getDetailPage(@PathVariable String challengeNo){


        return ResponseEntity.ok("");
    }

    @GetMapping("{challengeNo}/review")
    public ResponseEntity<?> getReview(@PathVariable String challengeNo){


        return ResponseEntity.ok("missionList");
    }

    @GetMapping("{challengeNo}/upload")
    public ResponseEntity<?> getUpload(@PathVariable String challengeNo){


        return ResponseEntity.ok("missionList");
    }


    /*
    //todo challenge에 따른 missionList 구현
    @GetMapping("/{challengeNo}/ui-missionlist")
    public ResponseEntity<?> getUIMissionList(@PathVariable String challengeNo){

        List<Map<String, String>> missionList = missionService.getUIMissionList(challengeNo);

        return ResponseEntity.ok(missionList);
    }

    @GetMapping("/{challengeNo}/ux-missionlist")
    public ResponseEntity<?> getUXMissionList(@PathVariable String challengeNo){
        List<Map<String, String>> missionList = missionService.getUXMissionList(challengeNo);

        return ResponseEntity.ok(missionList);
    }
    */

}
