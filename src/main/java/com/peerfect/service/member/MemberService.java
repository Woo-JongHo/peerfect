package com.peerfect.service.member;


import com.peerfect.DTO.MemberChallengeDTO;
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


    public boolean authenticate(String email) {
        return MemberRepository.authenticate(email);
    }

    public List<MemberChallengeDTO> getMemberMain(String memberId) {
        return MemberRepository.getMemberMain(memberId);
    }

    public List<MemberChallengeDTO> getMemberNext(String memberId) {
        return MemberRepository.getMemberNext(memberId);

    }
    public List<MemberChallengeDTO> getMemberComplete(String memberId) {
        return MemberRepository.getMemberComplete(memberId);
    }

    public boolean isNickNameExist(String nickname) {
        return MemberRepository.isNickNameExist(nickname);
    }

    public String getMemberId(String email) {
        return MemberRepository.getMemberId(email);
    }
    public String getMemberNickName(String email) {
        return MemberRepository.getMemberNickName(email);
    }

    public void deleteMember(String memberId) {
        MemberRepository.deleteById(memberId);

    }
}

