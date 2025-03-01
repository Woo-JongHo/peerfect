package com.peerfect.db.member;

import com.peerfect.DTO.MemberChallengeDTO;
import com.peerfect.vo.member.MemberVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

import java.time.LocalDateTime;
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

    public static boolean isNickNameExist(String nickname) {
        boolean exists = false;
        SqlSession session = sqlSessionFactory.openSession();
        log.info(nickname + " 뭐야 안나와?");
        exists = session.selectOne("member.isNickNameExist", nickname);

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

    public static int deleteById(String memberId) {
        int re = -1;
        log.info("여기가 실행되는지 확인");
        SqlSession session = sqlSessionFactory.openSession();
        try {
            re = session.delete("member.deleteMember", memberId);
            session.commit();
            System.out.println("삭제된 행 개수: " + re);
        } catch (Exception e) {
            System.err.println("삭제 실패: " + e.getMessage());
            session.rollback();
        } finally {
            session.close();
        }

        return re;
    }
    public static void updateUIStart(String memberId, String challengeNo) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            Map<String, String> params = new HashMap<>();
            params.put("memberId", memberId);
            params.put("challengeNo", challengeNo);

            int re = session.update("member.updateUIStart", params);
            session.commit();
            log.info("UI 챌린지 시작 시간 업데이트 완료: memberId={}, challengeNo={}", memberId, challengeNo);
        } catch (Exception e) {
            log.error("UI 챌린지 시작 시간 업데이트 실패: {}", e.getMessage());
            session.rollback();
        } finally {
            session.close();
        }
    }

    public static void updateUXStart(String memberId, String challengeNo) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            Map<String, String> params = new HashMap<>();
            params.put("memberId", memberId);
            params.put("challengeNo", challengeNo);

            int re = session.update("member.updateUXStart", params);
            session.commit();
            log.info("UX 챌린지 시작 시간 업데이트 완료: memberId={}, challengeNo={}", memberId, challengeNo);
        } catch (Exception e) {
            log.error("UX 챌린지 시작 시간 업데이트 실패: {}", e.getMessage());
            session.rollback();
        } finally {
            session.close();
        }
    }

    public static Map<String, Object> getMemberInfo(String memberId) {
        SqlSession session = sqlSessionFactory.openSession();
        log.info("여기 db memberId={}", memberId);
        Map<String, Object> memberData = null;

        try {
            memberData = session.selectOne("member.getMemberInfo", memberId);
        } catch (Exception e) {
            System.err.println("회원 정보 조회 실패: " + e.getMessage());
        } finally {
            session.close();
        }

        return memberData;
    }

    public static boolean isMemberExist(String memberId) {
        boolean exists = false;
        SqlSession session = sqlSessionFactory.openSession();
        exists = session.selectOne("member.isMemberExist", memberId);

        log.info("Nickname existence check result: {}", exists);

        return exists;
    }

    public static HashMap<String, Object> startMemberChallenge(String memberId, String challengeNo, String type) {
        SqlSession session = sqlSessionFactory.openSession();
        HashMap<String, Object> response = new HashMap<>();
        int updatedRows = 0;
        int number = Integer.parseInt(challengeNo);
        LocalDateTime now = LocalDateTime.now();

        try {
            Map<String, Object> params = new HashMap<>();
            params.put("memberId", memberId);
            params.put("challengeNo", number);

            // 타입에 따라 ui 또는 ux 시작 시간 업데이트
            if (type.equals("ui")) {
                params.put("field", "member_uistart");
            } else if (type.equals("ux")) {
                params.put("field", "member_uxstart");
            } else {
                throw new IllegalArgumentException("유효하지 않은 타입입니다. (ui 또는 ux만 허용)");
            }
            params.put("startTime", now.toString());  // LocalDateTime -> String 변환

            // 동적 SQL로 필드 업데이트 (ui 또는 ux)
            session.update("member.dynamicUpdateStart", params);

            // challengeNo 업데이트
            updatedRows = session.update("member.updateMemberChallenge", params);
            session.commit();

            if (updatedRows > 0) {
                response.put("message", "챌린지가 성공적으로 시작되었습니다.");
                response.put("status", "success");
            } else {
                response.put("message", "존재하지 않는 회원 ID입니다.");
                response.put("status", "fail");
            }
        } catch (Exception e) {
            session.rollback();
            response.put("message", "챌린지 시작 중 오류가 발생했습니다: " + e.getMessage());
            response.put("status", "error");
        } finally {
            session.close();
        }

        return response;
    }

    public static String stopMemberChallenge(String memberId) {
        String result = "fail";

        try (SqlSession session = sqlSessionFactory.openSession()) {

            Map<String, Object> params = new HashMap<>();
            params.put("memberId", memberId);

            int updatedRows = session.update("member.stopMemberChallenge", params);

            session.commit(); // 트랜잭션 커밋

            result = (updatedRows > 0) ? "success" : "fail";

        } catch (Exception e) {
            log.error("챌린지 중지 중 오류 발생: {}", e.getMessage());
        }

        return result;
    }
}
