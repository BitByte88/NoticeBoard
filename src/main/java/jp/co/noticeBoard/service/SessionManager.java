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
import jp.co.noticeBoard.dto.UserDto;
import jp.co.noticeBoard.form.OrderListForm;

/**
 * セッション変数管理.
 */
@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionManager implements InitializingBean {

    @Autowired
    private HttpSession session;

    public void clearSession() {
        session.invalidate();
    }

    /**
     * セッション情報「userInfo」を設定する。
     *
     * @param userInfo セッション情報「管理者ログイン情報」
     */
    public void setSesUserInfo(UserDto userInfo) {
        this.session.setAttribute(Const.SES_USER_INFO, userInfo);
    }

    /**
     * セッション情報「userInfo」を取得する。
     *
     */
    public UserDto getSesUserInfo() {

        return (UserDto)this.session.getAttribute(Const.SES_USER_INFO);
    }

    /**
     * セッション情報「現在時間」を設定する。
     *
     */
    public void setSesTime() {
        SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy/MM/dd HH:mm:ss");
        Calendar time = Calendar.getInstance();

        String format_time = format1.format(time.getTime());
        this.session.setAttribute(Const.SES_TIME, format_time);
    }


    /**
     * セッション情報「orderListForm」を設定する。
     *
     * @param orderListForm セッション情報「注文状況一覧検索条件」
     */
    public void setSesOrderListSearchInfo(OrderListForm orderListForm) {
        this.session.setAttribute(Const.SES_ORDER_SEARCH_INFO, orderListForm);
    }

    /**
     * セッション情報「orderListForm」を取得する。
     *
     */
    public OrderListForm getSesOrderListSearchInfo() {
        OrderListForm orderListForm =(OrderListForm)this.session.getAttribute(Const.SES_ORDER_SEARCH_INFO);
        return orderListForm;
    }

    /**
     * セッション情報「userInfo」を削除する。
     *
     */
    public void clearUserInfo() {
        this.session.removeAttribute(Const.SES_USER_INFO);
    }


    @Override
    public void afterPropertiesSet() throws Exception {

    }

}

