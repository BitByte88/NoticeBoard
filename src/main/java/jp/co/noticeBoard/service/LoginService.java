package jp.co.noticeBoard.service;


import jp.co.noticeBoard.dto.UserDto;
import jp.co.noticeBoard.form.LoginForm;
import jp.co.noticeBoard.dao.mapper.LoginMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    private LoginMapper loginMapper;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private CommonService commonService;


    /**
     * 管理者情報を取得する。
     *
     * @param account    管理者アカウント
     * @return 管理者情報
     */
    public UserDto getUserInfo(String account) {

        return loginMapper.getUserInfo(account);

    }

    /**
     * パスワードをSHA-256アルゴリズムでハッシュ化する。
     *
     * @param password    管理者パスワード
     * @return ハッシュ化されたパスワード
     */
    public String getHash(String password) throws NoSuchAlgorithmException {
        String retVal = "";
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());

            byte byteData[] = md.digest();
            StringBuffer sb = new StringBuffer();

            for(int i=0; i<byteData.length; i++) {
                sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
            }

            StringBuffer hexString = new StringBuffer();
            for(int i=0; i<byteData.length;i++) {
                String hex = Integer.toHexString(0xff & byteData[i]);
                if(hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            retVal = hexString.toString();
        return retVal;
    }

    /**
     * アカウント、パスワードチェック
     *
     * @param loginForm ログイン画面のForm
     * @param locale   リクエストヘッダーのAccept-Language
     * @return エラーメッセージリスト
     */
    public List<String> loginInputCheck(LoginForm loginForm,Locale locale) throws Exception {
        // エラーメッセージリスト
        List<String> messageList = new ArrayList<>();

        String account = loginForm.getAccount();
        String password = loginForm.getPassword();
        //アカウント、パスワード入力チェック
        if (StringUtils.isEmpty(account)||StringUtils.isEmpty(password)) {

            messageList.add( messageSource.getMessage("E09995", new Object[] {}, locale));
            logger.error(messageSource.getMessage("E09995", new Object[] {}, locale));
        }

        return messageList;
    }


}