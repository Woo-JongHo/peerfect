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

    public static int insertReview(ReviewVO reviewVO) {
        int result = 0;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            result = session.insert("review.insertReview", reviewVO);
            session.commit();
            log.info("Review inserted successfully: " + reviewVO);
        } catch (Exception e) {
            session.rollback();
            log.error("Error inserting review: ", e);
        } finally {
            session.close();
        }
        return result;
    }
}
