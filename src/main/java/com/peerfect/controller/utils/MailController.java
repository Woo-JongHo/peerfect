package com.peerfect.controller.utils;

import com.peerfect.DTO.MailDTO;
import com.peerfect.service.utils.MailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.io.UnsupportedEncodingException;
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/email")

public class MailController {

    private final MailService mailService;

    @ResponseBody
    @PostMapping("/Check")
    public String emailCheck(@RequestBody MailDTO mailDTO) throws MessagingException, UnsupportedEncodingException {
            String authCode = mailService.sendSimpleMessage(mailDTO.getEmail());

        log.info(authCode + ": authChode");
        return authCode;
    }
}
