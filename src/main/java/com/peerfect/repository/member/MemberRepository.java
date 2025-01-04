package com.peerfect.repository.member;


import com.peerfect.db.member.MemberDBManger;
import com.peerfect.vo.member.MemberVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberRepository {

    public static void insertUser(MemberVO userVO) {
        MemberDBManger.insertUser(userVO);
    }

    public static boolean isMailExists(String email) {
        return MemberDBManger.isMailExists(email);
    }

    public static String getMemberId(String email) {
        return MemberDBManger.getMemberId(email);
    }

    public static boolean authenticate(String email) {
        return MemberDBManger.authenticate(email);
    }

    public static List<Map<String, String>> getMemberComplete(String userId) {
        return MemberDBManger.getMemberComplete(userId);
    }

    public static List<Map<String, String>> getMemberMission(String memberId) {
        return MemberDBManger.getMemberMission(memberId);
    }
}
