package jp.co.noticeBoard.dto;

import lombok.Data;

@Data
public class UserJoinDto {

    //ユーザー番号
    private Integer userNo;

    //ID
    private String userId;

    //パスワード
    private String password ;

    //名前
    private String name;

    //生年月日
    private String userBirthday;

    //性別
    private String gender;

    //メール
    private String mail;

    //加入理由
    private String joinReason ;

}
