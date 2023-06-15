package jp.co.noticeBoard;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoginConfig implements WebMvcConfigurer {

    @Autowired
    @Qualifier(value = "loginInterceptor")
    private HandlerInterceptor loginInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> notLoadList = new ArrayList<>();
        notLoadList.add("/BO");
        notLoadList.add("/BO/login");
        notLoadList.add("/BO/returnLogin");
        notLoadList.add("/BO/logout");
        registry.addInterceptor(loginInterceptor).addPathPatterns("/BO/**").excludePathPatterns(notLoadList);


    }
}

