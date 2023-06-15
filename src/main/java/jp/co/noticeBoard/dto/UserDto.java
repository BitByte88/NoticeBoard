package jp.co.noticeBoard.dto;

import lombok.Data;

@Data
public class UserDto {

    /**
     * 管理者番号
     */
    private Integer userNo;

    /**
     * 管理者アカウント
     */
    private String userId;

    /**
     * 管理者パスワード
     */
    private String password;

    /**
     * 管理者氏名
     */
    private String name;
}
