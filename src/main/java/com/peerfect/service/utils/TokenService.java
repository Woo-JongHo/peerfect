package com.peerfect.service.utils;

import ch.qos.logback.core.subst.Token;
import com.peerfect.db.utils.TokenDBManger;
import com.peerfect.repository.member.MemberRepository;
import com.peerfect.repository.utils.TokenRepository;
import com.peerfect.util.JwtTokenProvider;
import com.peerfect.vo.utils.TokenVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenRepository tokenRepository;
    public void saveToken(TokenVO tokenVO) {
        tokenRepository.saveToken(tokenVO);
    }

    public String getAccessToken(String memberId) {
        return tokenRepository.getAccessToken(memberId);
    }

    public String getRefreshToken(String memberId) {
        return tokenRepository.getRefreshToken(memberId);

    }


    public String regenerateAccessToken(String refreshToken) {

        log.info("refreshToken Access " + refreshToken);
        if (!tokenRepository.checkRefreshByToken(refreshToken)) {
            throw new RuntimeException("Refresh token mismatch");
        }

        String memberId = tokenRepository.getMemberIdByToken(refreshToken);

        // 새로운 accessToken 생성
        String newAccessToken = jwtTokenProvider.generateAccessToken(memberId);

        // DB에서 accessToken 업데이트
        TokenDBManger.updateAccessToken(memberId, newAccessToken);

        return newAccessToken;
    }


    public Map<String, String> regenerateRefreshToken(String refreshToken) {
        /*
        if (!jwtTokenProvider.validateRefreshToken(refreshToken)) {
            throw new RuntimeException("Invalid or expired refresh token");
        }
           */
        log.info("Received refreshToken: {}", refreshToken);

        String memberId = tokenRepository.getMemberIdByToken(refreshToken);

        // DB에서 저장된 refreshToken 가져오기
        String storedRefreshToken = TokenDBManger.getRefreshToken(memberId);
        if (!refreshToken.equals(storedRefreshToken)) {
            throw new RuntimeException("Refresh token mismatch");
        }

        // 새로운 accessToken & refreshToken 생성
        String newAccessToken = jwtTokenProvider.generateAccessToken(memberId);
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(memberId);

        // DB에 새로운 refreshToken 저장
        TokenVO tokenVO = new TokenVO();
        tokenVO.setMemberId(UUID.fromString(memberId));
        tokenVO.setAccessToken(newAccessToken);
        tokenVO.setRefreshToken(newRefreshToken);
        TokenDBManger.saveToken(tokenVO);

        return Map.of("accessToken", newAccessToken, "refreshToken", newRefreshToken);
    }

    public void logout(String accessToken) {
        String memberId = jwtTokenProvider.getMemberIdFromToken(accessToken);
        tokenRepository.deleteTokensByMemberId(memberId);
    }
}
