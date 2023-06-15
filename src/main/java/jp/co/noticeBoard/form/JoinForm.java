package jp.co.noticeBoard.form;

import lombok.Data;

@Data
public class JoinForm {

    /**
     * UserId
     */
    private String userId;

    /**
     * PW
     */
    private String password;

    /**
     * 名前
     */
    private String name;

    /**
     * 生年月日
     */
    private String birthday;

    /**
     * 性別
     */
    private String Gender;

    /**
     * メール
     */
    private String mail;

    /**
     * 加入理由
     */
    private String joinReason;


}
