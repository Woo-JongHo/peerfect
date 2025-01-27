package com.peerfect.db.utils;
import com.peerfect.db.DBManger;
import com.peerfect.vo.utils.TokenVO;
import com.peerfect.vo.utils.VerifyVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class TokenDBManger extends DBManger{
    public static int saveToken(TokenVO tokenVO) {
        int re = -1;
        SqlSession session = sqlSessionFactory.openSession();
        re = session.insert("token.saveToken", tokenVO);
        session.commit();
        session.close();

        return re;
    }

    public static String getAccessToken(String memberId) {
        String token = "";
        SqlSession session = sqlSessionFactory.openSession();
        try {
            token = session.selectOne("token.getAccessToken", UUID.fromString(memberId));
        } catch (Exception e) {
            log.error("Error fetching access token: {}", e.getMessage());
        } finally {
            session.close();
        }

        return token;
    }

    public static String getRefreshToken(String memberId) {
        String token = "";
        SqlSession session = sqlSessionFactory.openSession();
        try {
            token = session.selectOne("token.getRefreshToken", UUID.fromString(memberId));
        } catch (Exception e) {
            log.error("Error fetching refresh token: {}", e.getMessage());
        } finally {
            session.close();
        }

        return token;
    }
}
