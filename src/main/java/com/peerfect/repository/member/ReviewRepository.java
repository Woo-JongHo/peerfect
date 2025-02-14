package com.peerfect.repository.member;


import com.peerfect.db.member.ReviewDBManger;
import com.peerfect.vo.challenge.ReviewVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ReviewRepository {
    public List<ReviewVO> getReviews(String challengeNo) {
        return ReviewDBManger.getReviews(challengeNo);
    }


}
