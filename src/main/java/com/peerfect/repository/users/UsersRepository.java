package com.peerfect.repository.users;


import com.peerfect.db.users.UsersDBManger;
import com.peerfect.vo.users.MemberVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UsersRepository {

    public static void insertUser(MemberVO userVO) {
        UsersDBManger.insertUser(userVO);
    }
}
