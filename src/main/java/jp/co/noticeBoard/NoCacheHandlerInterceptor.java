package jp.co.noticeBoard;

import jp.co.noticeBoard.utils.CommonUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class NoCacheHandlerInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(HttpServletRequest request
            , HttpServletResponse response
            , Object handler
            , ModelAndView modelAndView) throws Exception {
        CommonUtils.setResponseNoCache(response);
    }
}
