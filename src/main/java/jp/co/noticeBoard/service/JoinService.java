package jp.co.noticeBoard.service;

import jp.co.noticeBoard.dao.mapper.JoinMapper;
import jp.co.noticeBoard.dto.UserJoinDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private static final Logger logger = LoggerFactory.getLogger(JoinService.class);

    @Autowired
    private JoinMapper joinMapper;


    public void joinUser(UserJoinDto userJoinDto) throws Exception {

        joinMapper.insertUserInfo(userJoinDto);

    }

    public int idCheck(String userId) {
        int checkedId = joinMapper.idCheck(userId);
        return checkedId;
    }


}
