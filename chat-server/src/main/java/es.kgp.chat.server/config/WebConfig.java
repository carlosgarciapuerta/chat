package es.kgp.chat.server.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by kgp on 19/01/2014.
 */
@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan("es.kgp.chat.server.controller")
public class WebConfig extends WebMvcConfigurerAdapter{



    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(securityInterceptor);
    }
}
