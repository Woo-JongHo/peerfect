package com.peerfect.service.users;


import com.peerfect.repository.users.UsersRepository;
import com.peerfect.vo.users.UsersVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersService{


    public void insertUser(UsersVO userVO) {
        UsersRepository.insertUser(userVO);
    }
}

