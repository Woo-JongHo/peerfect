package com.peerfect.repository.utils;

import com.peerfect.db.utils.MailDBManger;
import com.peerfect.vo.utils.VerifyVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MailRepostiory {
    public void setEmailVerify(VerifyVO v) {

        MailDBManger.setEmailVerify(v);
    }

    public int getEmailVerify(String email, String authCode) {
        int re = MailDBManger.getEmailVerify(email, authCode);

        return re;
    }

    public void deleteEmailVerify(String email) {
        MailDBManger.deleteEmailVerify(email);
    }
}
