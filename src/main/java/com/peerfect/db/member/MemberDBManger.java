package com.peerfect.db.member;

import com.peerfect.DTO.MemberChallengeDTO;
import com.peerfect.vo.member.MemberVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.peerfect.db.DBManger.sqlSessionFactory;
@Slf4j

public class MemberDBManger {
    public static int insertUser(MemberVO memberVO) {
        int re = -1;

        SqlSession session = sqlSessionFactory.openSession();
        re = session.insert("member.insert", memberVO);
        session.commit();
        session.close();

        return re;
    }

    public static boolean isMailExists(String memberEmail) {
        boolean exists = false;
        SqlSession session = sqlSessionFactory.openSession();

        try {
            exists = session.selectOne("member.isMailExists", memberEmail);
        } catch (Exception e) {
            log.error("Error checking email existence: {}", e.getMessage());
        } finally {
            session.close();
        }

        return exists;
    }

    public static String getMemberId(String email) {
        String memberId = null;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            memberId = session.selectOne("member.getMemberId", email);
            if (memberId == null || !isValidUUID(memberId)) {
                log.warn("Invalid or missing member ID for email: {}", email);
                throw new IllegalArgumentException("Invalid or missing member ID");
            }
        } catch (Exception e) {
            log.error("Error fetching member ID: {}", e.getMessage());
            throw e; // 예외를 상위로 전달
        } finally {
            session.close();
        }

        return UUID.fromString(memberId).toString();
    }

    private static boolean isValidUUID(String str) {
        try {
            UUID.fromString(str);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static boolean authenticate(String email) {
        boolean exists = false;
        SqlSession session = sqlSessionFactory.openSession();

        try {
            Map<String, String> params = new HashMap<>();
            params.put("email", email);

            exists = session.selectOne("member.authenticate", params);
        } catch (Exception e) {
            log.error("Error authenticating user: {}", e.getMessage());
        } finally {
            session.close();
        }

        return exists;
    }

    public static List<MemberChallengeDTO> getMemberMain(String memberId) {

        List<MemberChallengeDTO> result = null;
        SqlSession session = sqlSessionFactory.openSession();
        result = session.selectList("member.getMemberMain", memberId);

        session.close();
        return result;
    }

    public static List<MemberChallengeDTO> getMemberNext(String memberId) {
        List<MemberChallengeDTO> result = null;
        SqlSession session = sqlSessionFactory.openSession();
        result = session.selectList("member.getMemberNext", memberId);

        session.close();
        return result;
    }
    public static List<MemberChallengeDTO> getMemberComplete(String memberId) {
        List<MemberChallengeDTO> result = null;
        SqlSession session = sqlSessionFactory.openSession();
        result = session.selectList("member.getMemberComplete", memberId);

        session.close();
        return result;
    }

    public static boolean isNickNameExist(String name) {
        boolean exists = false;
        SqlSession session = sqlSessionFactory.openSession();
        log.info(name);
        exists = session.selectOne("member.isNickNameExist", name);

        log.info("Nickname existence check result: {}", exists);

        return exists;
    }

    public static String getMemberNickName(String email) {
        String nickname = "";
        SqlSession session = sqlSessionFactory.openSession();
        try {
            nickname = session.selectOne("member.getMemberNickName", email);
        } catch (Exception e) {
            log.error("Error fetching member ID: {}", e.getMessage());
        } finally {
            session.close();
        }

        return nickname;

    }
}
