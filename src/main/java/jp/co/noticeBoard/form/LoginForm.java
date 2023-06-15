package jp.co.noticeBoard.form;

import lombok.Data;

@Data
public class LoginForm {

    /**
     * 管理者ID.
     */
    private String account;
    /**
     * パスワード
     */
    private String password;

}
