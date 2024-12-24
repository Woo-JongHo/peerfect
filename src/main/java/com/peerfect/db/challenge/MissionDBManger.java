package com.peerfect.db.challenge;

import com.peerfect.db.DBManger;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;
@Slf4j
public class MissionDBManger extends DBManger {
    public static List<Map<String, String>> getUIMissionList(String challengeNo) {
        List<Map<String, String>> result = null;
        SqlSession session = sqlSessionFactory.openSession();

        try {
            result = session.selectList("mission.getUIMissionList", challengeNo);

            log.info("result" + result);
        } catch (Exception e) {
            System.err.println("Error fetching UI mission list: " + e.getMessage());
        } finally {
            session.close();
        }
        return result;
    }

    public static List<Map<String, String>> getUXMissionList(String challengeNo) {
        List<Map<String, String>> result = null;
        SqlSession session = sqlSessionFactory.openSession();

        try {
            result = session.selectList("mission.getUXMissionList", challengeNo);
        } catch (Exception e) {
            System.err.println("Error fetching UX mission list: " + e.getMessage());
        } finally {
            session.close();
        }
        return result;
    }
}
