package com.peerfect.db.member;

import com.peerfect.vo.challenge.ReviewVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import java.util.List;
import static com.peerfect.db.DBManger.sqlSessionFactory;

@Slf4j
public class ReviewDBManger {

    public static List<ReviewVO> getReviews(String challengeNo) {
        List<ReviewVO> reviews;
        SqlSession session = sqlSessionFactory.openSession();
        reviews = session.selectList("review.getReviews", challengeNo);
        log.info(reviews.toString());
        session.close();

        return reviews;
    }
}
