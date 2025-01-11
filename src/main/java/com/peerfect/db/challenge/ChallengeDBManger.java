package com.peerfect.db.challenge;

import com.peerfect.DTO.ChallengeDetailDTO;
import com.peerfect.DTO.PreviewDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.peerfect.db.DBManger.sqlSessionFactory;

@Slf4j
public class ChallengeDBManger {
    public static List<PreviewDTO> getUIPreview(String preview) {
        List<PreviewDTO> result;
        SqlSession session = sqlSessionFactory.openSession();
        result = session.selectList("challenge.getUIPreview", preview);
        session.close();

        log.info(result.toString());
        return result;
    }

    public static List<PreviewDTO> getUXPreview(String preview) {
        List<PreviewDTO> result;
        SqlSession session = sqlSessionFactory.openSession();
        result = session.selectList("challenge.getUXPreview", preview);
        session.close();

        return result;
    }

    public static ChallengeDetailDTO getChallengeDetail(String challengeTitle) {
        ChallengeDetailDTO result;
        SqlSession session = sqlSessionFactory.openSession();
        int challengeNo = Integer.parseInt(challengeTitle);
        result = session.selectOne("challenge.getChallengeDetail", challengeNo);

        log.info(String.valueOf(result));
        session.close();

        return result;
    }

    public static String startMemberChallenge(String memberId, String challengeNo) {

        SqlSession session = sqlSessionFactory.openSession();
        Map<String, Object> params = new HashMap<>();
        params.put("memberId", memberId);
        params.put("challengeNo", Integer.parseInt(challengeNo));

        session.update("challenge.startMemberChallenge", params);
        session.commit();
        session.close();

        return "DB update success";
    }
}
