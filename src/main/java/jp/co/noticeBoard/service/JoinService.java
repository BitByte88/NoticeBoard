package jp.co.noticeBoard.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
    
}
