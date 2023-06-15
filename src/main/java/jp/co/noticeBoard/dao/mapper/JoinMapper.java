package jp.co.noticeBoard.dao.mapper;

import jp.co.noticeBoard.dto.UserJoinDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface JoinMapper {

    void insertUserInfo(UserJoinDto userJoinDto);
    int idCheck(String userId);

}
