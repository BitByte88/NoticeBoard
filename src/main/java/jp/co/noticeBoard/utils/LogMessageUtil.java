package jp.co.noticeBoard.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class LogMessageUtil {
    // メッセージ
    public static final String LOG_MSG_INFO_START="INFO0001";
    public static final String LOG_MSG_INFO_COMPLETE="INFO0002";

    public static final String LOG_MSG_ERROR_EMPTY="ERR1001";
    public static final String LOG_MSG_ERROR_FORMAT="ERR1002";
    public static final String LOG_MSG_ERROR_OVER_LENGTH="ERR1003";
    public static final String LOG_MSG_ERROR_MAIL_FAILED="ERR1004";
    public static final String LOG_MSG_ERROR_FAILED="ERR0005";

    public static final String LOG_MSG_MAIL_START="MAIL_START";


    @Autowired
    private MessageSource logMessage;

    /**
     * 引数無しのログメッセージを取得する
     * @param code メッセージコード
     * @return ログメッセージ
     */
    public String getMessage(String code){
        return logMessage.getMessage(code, ArrayUtils.EMPTY_OBJECT_ARRAY, null);
    }

    /**
     *　ログメッセージを取得する
     * @param code メッセージコード
     * @param args 引数（配列にしなくても可）
     * @return
     */
    public String getMessage(String code, Object... args){
        return logMessage.getMessage(code, args, null);
    }
}