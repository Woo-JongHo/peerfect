package com.peerfect.db.users;

import com.peerfect.vo.users.UsersVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

import static com.peerfect.db.DBManger.sqlSessionFactory;
@Slf4j

public class UsersDBManger {
    public static int insertUser(UsersVO userVO) {
        int re = -1;
        SqlSession session = sqlSessionFactory.openSession();
        re = session.insert("users.insert", userVO);
        session.commit();
        session.close();

        return re;
    }
}
