package com.peerfect.service.member;


import com.peerfect.repository.member.MemberRepository;
import com.peerfect.vo.member.MemberVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {


    public void insertUser(MemberVO userVO) {
        MemberRepository.insertUser(userVO);
    }
}

