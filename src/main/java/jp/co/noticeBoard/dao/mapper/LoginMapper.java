package jp.co.noticeBoard.dao.mapper;

import jp.co.noticeBoard.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 管理者ログインMapperクラス.
 */

@Mapper
public interface LoginMapper {

    /**
     * 管理者情報を取得する。
     *
     *
     * @param account 管理者アカウント
     */
    UserDto getUserInfo(@Param("account") String account);

}
