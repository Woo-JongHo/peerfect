package com.peerfect.db.users;

import com.peerfect.vo.member.MemberVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

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
}
