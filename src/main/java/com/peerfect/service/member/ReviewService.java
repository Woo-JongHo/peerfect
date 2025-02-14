package com.peerfect.service.member;

import com.peerfect.repository.member.ReviewRepository;
import com.peerfect.vo.challenge.ReviewVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    public List<ReviewVO> getReviews(String challengeNo) {
        return reviewRepository.getReviews(challengeNo);
    }
}

