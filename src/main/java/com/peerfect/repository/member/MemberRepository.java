package com.peerfect.repository.member;


import com.peerfect.DTO.MemberChallengeDTO;
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


    public static List<MemberChallengeDTO> getMemberMain(String memberId) {
        return MemberDBManger.getMemberMain(memberId);
    }


    public static List<MemberChallengeDTO> getMemberNext(String memberId) {
        return MemberDBManger.getMemberNext(memberId);
    }

    public static List<MemberChallengeDTO> getMemberComplete(String memberId) {
        return MemberDBManger.getMemberComplete(memberId);
    }


    public static boolean isNickNameExist(String nickname) {
        return MemberDBManger.isNickNameExist(nickname);
    }

    public static String getMemberNickName(String email) {
        return MemberDBManger.getMemberNickName(email);
    }

    public static void deleteById(String memberId) {
        MemberDBManger.deleteById(memberId);
    }

    public static void updateUIStart(String memberId, String ChallengeNo) {
        MemberDBManger.updateUIStart(memberId, ChallengeNo);

    }

    public static void updateUXStart(String memberId, String ChallengeNO) {
        MemberDBManger.updateUXStart(memberId, ChallengeNO);

    }

    public static Map<String, Object> getMemberInfo(String memberId) {

        Map<String, Object> memberData = MemberDBManger.getMemberInfo(memberId);

        log.info("조회된 회원 데이터: {}", memberData);


        return memberData;
    }

    public static boolean isMemberExists(String memberId) {
        return MemberDBManger.isMemberExist(memberId);
    }

}
