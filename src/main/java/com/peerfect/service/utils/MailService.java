package com.peerfect.service.utils;

import com.peerfect.repository.utils.MailRepostiory;
import com.peerfect.vo.utils.VerifyVO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {
    private final MailRepostiory mailRepository;
    private final JavaMailSender javaMailSender;
    private static final String senderEmail = "jonghowoo33@gmail.com";

    // 랜덤으로 숫자 생성
    public String createNumber() {
        Random random = new Random();
        StringBuilder key = new StringBuilder();

        for (int i = 0; i < 8; i++) { // 인증 코드 8자리
            int index = random.nextInt(3); // 0~2까지 랜덤, 랜덤값으로 switch문 실행

            switch (index) {
                case 0 -> key.append((char) (random.nextInt(26) + 97)); // 소문자
                case 1 -> key.append((char) (random.nextInt(26) + 65)); // 대문자
                case 2 -> key.append(random.nextInt(10)); // 숫자
            }
        }
        return key.toString();
    }

    public MimeMessage createMail(String mail, String number) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        // 발신자 이메일 설정
        message.setFrom(senderEmail);

        // 수신자 이메일 설정
        message.setRecipients(MimeMessage.RecipientType.TO, mail);

        // 이메일 제목 설정
        message.setSubject("Peerfect 회원가입 이메일 인증입니다");

        // 인증 링크 생성
        String baseUrl = "http://localhost:3000/auth";
        String link = String.format("%s?code=%s&email=%s", baseUrl, number, mail);

        // 이메일 본문 작성
        String body = "";
        body += "<h3>이메일 인증 요청입니다.</h3>";
        //body += "<p>아래 링크를 클릭하여 이메일 인증을 완료해주세요:</p>";
        //body += "<a href='" + link + "'>" + link + "</a>";
        //body += "<p>만약 링크가 클릭되지 않으면, 위 링크를 복사하여 브라우저에 붙여넣어주세요.</p>";
        body += "<p> 아래 인증번호를 복사하여 입력해주세요:</p>";
        body += "<strong>인증번호: " + number + "</strong>";
        body += "<h3>감사합니다.</h3>";

        // 이메일 내용 설정
        message.setText(body, "UTF-8", "html");

        return message;
    }

    // 메일 발송
    public String sendSimpleMessage(String sendEmail) throws MessagingException {
        String number = createNumber(); // 랜덤 인증번호 생성

        MimeMessage message = createMail(sendEmail, number); // 메일 생성
        try {
            javaMailSender.send(message); // 메일 발송
        } catch (MailException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("메일 발송 중 오류가 발생했습니다.");
        }

        return number; // 생성된 인증번호 반환
    }

    public void setEmailVerify(VerifyVO v) {
        mailRepository.setEmailVerify(v);
    }

    public int getEmailVerify(String email, String authCode) {
        int re = mailRepository.getEmailVerify(email, authCode);
        return re;
    }

    public void deleteEmailVerify(String email) {
        mailRepository.deleteEmailVerify(email);
    }
}
