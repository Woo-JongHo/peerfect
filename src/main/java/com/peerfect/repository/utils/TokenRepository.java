package com.peerfect.repository.utils;

import com.peerfect.db.utils.TokenDBManger;
import com.peerfect.vo.utils.TokenVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TokenRepository {
    public static void saveToken(TokenVO tokenVO) {
        TokenDBManger.saveToken(tokenVO);
    }

    public static String getAccessToken(String memberId) {
        return TokenDBManger.getAccessToken(memberId);
    }

    public static String getRefreshToken(String memberId) {
        return TokenDBManger.getRefreshToken(memberId);

    }
    public Optional<TokenVO> findByRefreshToken(String refreshToken) {
        return Optional.ofNullable(TokenDBManger.findByRefreshToken(refreshToken));
    }

    public boolean checkRefreshByToken(String refreshToken) {
        return TokenDBManger.checkRefreshToken(refreshToken);
    }

    public String getMemberIdByToken(String refreshToken) {
        return TokenDBManger.getMemberIdByToken(refreshToken);
    }
}
