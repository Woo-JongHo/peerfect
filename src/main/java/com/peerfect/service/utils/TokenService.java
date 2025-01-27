package com.peerfect.service.utils;

import com.peerfect.repository.utils.TokenRepository;
import com.peerfect.vo.utils.TokenVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {
    public void saveToken(TokenVO tokenVO) {
        TokenRepository.saveToken(tokenVO);
    }

    public String getAccessToken(String memberId) {
        return TokenRepository.getAccessToken(memberId);
    }

    public String getRefreshToken(String memberId) {
        return TokenRepository.getRefreshToken(memberId);

    }
}
