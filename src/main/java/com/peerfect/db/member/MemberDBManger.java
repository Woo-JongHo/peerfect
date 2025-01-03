package com.peerfect.db.member;

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
        String memberId = "";
        SqlSession session = sqlSessionFactory.openSession();
        try {
            memberId = session.selectOne("member.getMemberId", email);
        } catch (Exception e) {
            log.error("Error fetching member ID: {}", e.getMessage());
        } finally {
            session.close();
        }

        return UUID.fromString(memberId).toString(); // UUID 형식 변환
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
    public static List<Map<String, String>> getMemberComplete(String memberId) {
        List<Map<String, String>> result = null;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            result = session.selectList("complete.getMemberComplete", memberId);

            log.info("result" + result);
        } catch (Exception e) {
            System.err.println("Error fetching UI mission list: " + e.getMessage());
        } finally {
            session.close();
        }
        return result;
    }

    public static List<Map<String, String>> getMemberMission(String memberId) {
        List<Map<String, String>> result = null;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            result = session.selectList("member.getMemberMission", memberId);

            log.info("result" + result);
        } catch (Exception e) {
            System.err.println("Error fetching UI mission list: " + e.getMessage());
        } finally {
            session.close();
        }
        return result;
    }
}
