package com.peerfect.repository.member;


import com.peerfect.db.users.MemberDBManger;
import com.peerfect.vo.member.MemberVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberRepository {

    public static void insertUser(MemberVO userVO) {
        MemberDBManger.insertUser(userVO);
    }
}
