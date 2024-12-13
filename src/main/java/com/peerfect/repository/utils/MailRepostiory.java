package com.peerfect.repository.utils;

import com.peerfect.db.utils.MailDBManger;
import com.peerfect.vo.utils.EmailVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MailRepostiory {
    public void setEmailVerify(EmailVO ev) {

        MailDBManger.setEmailVerify(ev);
    }

    public int getEmailVerify(String email, String authCode) {
        MailDBManger.getEmailVerify(email, authCode);


    }
}
