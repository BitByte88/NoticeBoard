package jp.co.noticeBoard;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jp.co.noticeBoard.dto.UserDto;
import jp.co.noticeBoard.service.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;



@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private SessionManager sessionManager;

    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        logger.info("LoginInterceptor");
        UserDto userDto = sessionManager.getSesUserInfo();

        if(userDto != null) {
            return true;
        } else {
            try {
                String contextPath = request.getContextPath();
                response.sendRedirect(contextPath+"/BO/returnLogin");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;

        }
    }

}