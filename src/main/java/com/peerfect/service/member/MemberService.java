package com.peerfect.service.member;


import com.peerfect.repository.member.MemberRepository;
import com.peerfect.vo.member.MemberVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {


    public void insertUser(MemberVO userVO) {
        MemberRepository.insertUser(userVO);
    }

    public boolean isEmailExists(String email) {
        return MemberRepository.isMailExists(email);
    }

    public String getMemberId(String email) {
        return MemberRepository.getMemberId(email);
    }

    public boolean authenticate(String email) {
        return MemberRepository.authenticate(email);
    }

    public List<Map<String, String>> getMemberComplete(String userId) {
        return MemberRepository.getMemberComplete(userId);
    }

    public List<Map<String, String>> getMemberMission(String memberId) {
        return MemberRepository.getMemberMission(memberId);

    }
}

