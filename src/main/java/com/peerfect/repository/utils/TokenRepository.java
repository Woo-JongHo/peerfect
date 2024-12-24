package com.peerfect.repository.utils;

import com.peerfect.db.utils.TokenDBManger;
import com.peerfect.vo.utils.TokenVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TokenRepository {
    public static void saveToken(TokenVO tokenVO) {
        TokenDBManger.saveToken(tokenVO);
    }
}
