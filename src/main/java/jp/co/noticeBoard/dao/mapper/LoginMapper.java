package jp.co.noticeBoard.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.co.noticeBoard.dto.UserDto;

/**
 * ログインMapperクラス.
 */

@Mapper
public interface LoginMapper {

    /**
     * ユーザー情報を取得する。
     *
     *
     * @param account アカウント
     */
    UserDto getUserInfo(@Param("account") String account);

}
