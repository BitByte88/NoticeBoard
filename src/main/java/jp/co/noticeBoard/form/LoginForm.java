package jp.co.noticeBoard.form;

import lombok.Data;

@Data
public class LoginForm {

    /**
     * アカウント
     */
    private String account;
    /**
     * パスワード
     */
    private String password;

}
