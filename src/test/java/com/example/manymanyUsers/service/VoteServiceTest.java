package com.example.manymanyUsers.service;

import com.example.manymanyUsers.vote.service.VoteService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class VoteServiceTest {

    @Autowired VoteService voteService;
    @Autowired

    @Test
    public void 투표생성_성공() throws Exception {
        //given

        //when

        //then
    }
}
