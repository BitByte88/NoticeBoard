package jp.co.noticeBoard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ConnectConfig implements WebMvcConfigurer {

    @Autowired
    @Qualifier(value = "ConnectInterceptor")
    private HandlerInterceptor ConnectInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> notLoadList = new ArrayList<>();
        notLoadList.add("/login");
        notLoadList.add("/returnLogin");
        notLoadList.add("/logout");
        registry.addInterceptor(ConnectInterceptor).addPathPatterns("/").excludePathPatterns(notLoadList);
    }
}

