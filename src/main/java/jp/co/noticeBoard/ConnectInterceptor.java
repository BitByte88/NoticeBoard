package jp.co.noticeBoard;

import jp.co.noticeBoard.dto.UserDto;
import jp.co.noticeBoard.service.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class ConnectInterceptor implements HandlerInterceptor {

    @Autowired
    private SessionManager sessionManager;

    private static final Logger logger = LoggerFactory.getLogger(ConnectInterceptor.class);


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        logger.info("ConnectInterceptor");
        UserDto userDto = sessionManager.getSesUserInfo();

        if(userDto != null) {
            return true;
        } else {
            try {
                String contextPath = request.getContextPath();
                response.sendRedirect(contextPath+"/boardList");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
    }
}