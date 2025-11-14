package com.example.rebuild_day1.manual.config;

import com.example.rebuild_day1.manual.servlet.HelloServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;

public class ManualServletConfig {

    public static ServletRegistrationBean<HelloServlet> hello() {
        ServletRegistrationBean<HelloServlet> bean =
                new ServletRegistrationBean<>(new HelloServlet(), "/manual");
        bean.setLoadOnStartup(1);
        return bean;
    }
}