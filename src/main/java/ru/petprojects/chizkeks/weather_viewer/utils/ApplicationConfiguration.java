package ru.petprojects.chizkeks.weather_viewer.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.petprojects.chizkeks.weather_viewer.filter.AuthenticationFilter;
import ru.petprojects.chizkeks.weather_viewer.service.SessionService;

@Configuration
public class ApplicationConfiguration {

    //private static ApplicationContext applicationContext;

    @Autowired
    private SessionService sessionService;

    @Bean
    public FilterRegistrationBean<AuthenticationFilter> loggingFilter(){
        FilterRegistrationBean<AuthenticationFilter> authenticationBean
                = new FilterRegistrationBean<>();

        authenticationBean.setFilter(new AuthenticationFilter(sessionService));
        authenticationBean.addUrlPatterns("/home");

        return authenticationBean;
    }

}
