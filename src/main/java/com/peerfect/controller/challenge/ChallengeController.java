package com.peerfect.controller.challenge;

import com.peerfect.DTO.ChallengeDetailDTO;
import com.peerfect.DTO.PreviewDTO;
import com.peerfect.service.challenge.ChallengeService;
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
    String preview;

    @GetMapping("/")
    public String getChallenge() {
        return "Test";
    }


    @GetMapping("/ui-preview")
    public ResponseEntity<List<PreviewDTO>> getUIPreview() {
        String preview = "UI: 한걸음부터";

        List<PreviewDTO> list = challengeService.getUIPreview(preview);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/ux-preview")
    public ResponseEntity<List<PreviewDTO>> getUXpreview(){
        preview = "UX 초심자를 위한 2주 챌린지";
        List<PreviewDTO> list = challengeService.getUXPreview(preview);
        log.info(list + "list");
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{challengeNo}/detail")
    public ResponseEntity<ChallengeDetailDTO> getChallengeDetail(@PathVariable String challengeNo) {
        ChallengeDetailDTO list = challengeService.getChallengeDetail(challengeNo);

        return ResponseEntity.ok(list);
    }

    @GetMapping("{challengeNo}/review")
    public ResponseEntity<?> getReview(@PathVariable int challengeNo){


        return ResponseEntity.ok("missionList");
    }

    @GetMapping("{challengeNo}/upload")
    public ResponseEntity<?> getUpload(@PathVariable String challengeNo){


        return ResponseEntity.ok("missionList");
    }

}
