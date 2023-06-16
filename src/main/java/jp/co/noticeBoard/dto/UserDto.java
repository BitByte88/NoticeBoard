package jp.co.noticeBoard.dto;

import lombok.Data;

@Data
public class UserDto {

    /**
     * ユーザー番号
     */
    private Integer userNo;

    /**
     * アカウント
     */
    private String userId;

    /**
     * パスワード
     */
    private String password;

    /**
     * 氏名
     */
    private String name;
}
