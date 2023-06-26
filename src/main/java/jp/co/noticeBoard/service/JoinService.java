package jp.co.noticeBoard.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.co.noticeBoard.common.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.noticeBoard.dao.mapper.JoinMapper;
import jp.co.noticeBoard.dto.UserJoinDto;

@Service
public class JoinService {

    private static final Logger logger = LoggerFactory.getLogger(JoinService.class);

    @Autowired
    private JoinMapper joinMapper;


    public void joinUser(UserJoinDto userJoinDto) throws Exception {
        joinMapper.insertUserInfo(userJoinDto);
    }

    public int idCheck(String userId) {
        int cnt = joinMapper.idCheck(userId);
        return cnt;
    }

    /**
     * パスワードをSHA-256アルゴリズムでハッシュ化する。
     *
     * @param password    パスワード
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
     * 日付の妥当性チェックを行います。
     * 指定した日付文字列（yyyyMMdd）が
     * カレンダーに存在するかどうかを返します。
     * @param strDate チェック対象の文字列
     * @return 存在する日付の場合true
     */
    public boolean checkDate(String strDate)
    {
        DateFormat format = new SimpleDateFormat(Const.FORMAT_DATE_YYYYMMDD);

        format.setLenient(false);
        try {
            format.parse(strDate);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * メールアドレスの妥当性チェックを行います。
     * @param strMail チェック対象の文字列
     * @return パータンにマッチする場合true
     */
    public boolean checkMail(String strMail)
    {
        Pattern pattern = Pattern.compile(Const.MAIL_FORMAT);
        Matcher matcher = pattern.matcher(strMail);

        if (matcher.find()) {
            return true;

        } else {
            return false;
        }
    }
}
