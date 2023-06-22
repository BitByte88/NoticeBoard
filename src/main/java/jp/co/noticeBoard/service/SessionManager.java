package jp.co.noticeBoard.service;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.ibm.icu.util.Calendar;

import jp.co.noticeBoard.common.Const;
import jp.co.noticeBoard.dto.BoardListSearchDto;
import jp.co.noticeBoard.dto.UserDto;

/**
 * セッション変数管理.
 */
@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionManager implements InitializingBean {

    @Autowired
    private HttpSession session;

    /**
     * セッション情報クリア
     */
    public void clearSession() {
        session.invalidate();
    }

    /**
     * セッション情報へ「ユーザー情報」を設定する。
     *
     * @param userInfo セッション情報「ユーザー情報」
     */
    public void setSesUserInfo(UserDto userInfo) {
        this.session.setAttribute(Const.SES_USER_INFO, userInfo);
    }

    /**
     * セッション情報から「ユーザー情報」を取得する。
     */
    public UserDto getSesUserInfo() {

        return (UserDto)this.session.getAttribute(Const.SES_USER_INFO);
    }

    /**
     * セッション情報から「ユーザー情報」を削除する。
     *
     */
    public void clearUserInfo() {
        this.session.removeAttribute(Const.SES_USER_INFO);
    }

    /**
     * セッション情報へ「現在時間」を設定する。
     */
    public void setSesTime() {
        SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy/MM/dd HH:mm:ss");
        Calendar time = Calendar.getInstance();

        String format_time = format1.format(time.getTime());
        this.session.setAttribute(Const.SES_TIME, format_time);
    }


    /**
     * セッション情報へ「掲示情報の検索条件」を設定する。
     *
     * @param boardListsearchDto セッション情報「掲示情報の検索条件」
     */
    public void setSesBoardListSearchInfo(BoardListSearchDto boardListsearchDto) {
        this.session.setAttribute(Const.SES_SEARCH_INFO, boardListsearchDto);
    }

    /**
     * セッション情報から「掲示情報の検索条件」を取得する。
     */
    public BoardListSearchDto getSesBoardListSearchInfo() {
    	BoardListSearchDto boardListSearchDto =(BoardListSearchDto)this.session.getAttribute(Const.SES_SEARCH_INFO);
        return boardListSearchDto;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

}

