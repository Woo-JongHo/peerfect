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






    //todo member에 따른 챌린지 보여주기
    @GetMapping("/ux/{memberId}")
    public ResponseEntity<?> getMemberUXMission(@PathVariable String memberId){
        return ResponseEntity.ok("");

    }

    @GetMapping("/ui/{memberId}")
    public ResponseEntity<?> getMemberUIMission(@PathVariable String memberId){
        return ResponseEntity.ok("");

    }

}
