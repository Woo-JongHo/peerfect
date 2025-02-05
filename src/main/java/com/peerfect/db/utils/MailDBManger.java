package com.peerfect.db.utils;

import com.peerfect.db.DBManger;
import com.peerfect.vo.utils.VerifyVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MailDBManger extends DBManger {
    public static int setEmailVerify(VerifyVO v) {

        int re = -1;
        SqlSession session = sqlSessionFactory.openSession();
        re = session.insert("verify.setVerify", v);
        session.commit();
        session.close();

        return re;
    }

    public static int getEmailVerify(String email, String authCode) {
        int re = -1;
        SqlSession session = sqlSessionFactory.openSession();

        try {
            Map<String, String> params = new HashMap<>();
            params.put("email", email);
            params.put("authCode", authCode);

            re = session.selectOne("verify.getVerify", params);
        } finally {
            session.close();
        }
        return re;
    }

    public static void deleteEmailVerify(String email) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            session.delete("verify.deleteVerify", email);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace(); // 에러 출력
            session.rollback(); // 에러 발생 시 롤백
        } finally {
            session.close(); // 세션 닫기
        }

    }
}