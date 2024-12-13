package com.peerfect.controller.users;

import com.peerfect.service.utils.MailService;
import com.peerfect.service.users.UsersService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UsersController {
    private final MailService mailService;
    private final UsersService usersService;


}
