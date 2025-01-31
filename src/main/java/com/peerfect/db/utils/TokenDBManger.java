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
        try {
            // 먼저 기존 데이터가 있는지 확인
            String existingToken = session.selectOne("token.getRefreshToken", tokenVO.getMemberId());

            if (existingToken != null) {
                // 기존 토큰이 존재하면 업데이트
                re = session.update("token.updateToken", tokenVO);
            } else {
                // 기존 토큰이 없으면 새로 추가
                re = session.insert("token.saveToken", tokenVO);
            }
            session.commit();
        } catch (Exception e) {
            log.error("Error saving token: {}", e.getMessage());
        } finally {
            session.close();
        }
        return re;
    }

    public static String getAccessToken(String memberId) {
        String token = "";
        SqlSession session = sqlSessionFactory.openSession();
        try {
            token = session.selectOne("token.getAccessToken", memberId);
        } catch (Exception e) {
            log.error("Error fetching member ID: {}", e.getMessage());
        } finally {
            session.close();
        }

        return token;

    }

    public static String getRefreshToken(String memberId) {
        String token = "";
        SqlSession session = sqlSessionFactory.openSession();
        try {
            token = session.selectOne("token.getRefreshToken",memberId);
        } catch (Exception e) {
            log.error("Error fetching member ID: {}", e.getMessage());
        } finally {
            session.close();
        }

        return token;

    }

    public static TokenVO findByRefreshToken(String refreshToken) {
        SqlSession session = sqlSessionFactory.openSession();
        TokenVO tokenVO = null;
        try {
            tokenVO = session.selectOne("token.findByRefreshToken", refreshToken);
        } catch (Exception e) {
            log.error("Error fetching TokenVO by refreshToken: {}", e.getMessage());
        } finally {
            session.close();
        }
        return tokenVO;
    }
    public static void updateAccessToken(String memberId, String newAccessToken) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("memberId", memberId);
            params.put("accessToken", newAccessToken);

            int updatedRows = session.update("token.updateAccessToken", params);
            session.commit();

            if (updatedRows == 0) {
                log.error("Failed to update accessToken for memberId: {}", memberId);
            }
        } catch (Exception e) {
            log.error("Error updating accessToken: {}", e.getMessage());
        } finally {
            session.close();
        }
    }
}
