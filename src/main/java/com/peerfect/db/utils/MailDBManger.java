package com.peerfect.db.utils;

import com.peerfect.db.DBManger;
import com.peerfect.vo.utils.EmailVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MailDBManger extends DBManger {
    public static int setEmailVerify(EmailVO ev) {

        int re = -1;
        SqlSession session = sqlSessionFactory.openSession();
        re = session.insert("email.setVerify", ev);
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

            re = session.selectOne("email.getVerify", params);
        } finally {
            session.close();
        }
        return re;
    }

}