package com.wyu.counselsystem.config;

import com.wyu.counselsystem.security.MyAuthenticationProvide;
import com.wyu.counselsystem.security.MyUsernamePasswordFilter;
import com.wyu.counselsystem.service.impl.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author afglow
 * @Date Create in 2023-01-2023/1/29-14:51
 * @Description
 */
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailService userDetailService;


//    //注入数据源
//    @Autowired
//    private DataSource dataSource;
//
//    @Bean
//    public PersistentTokenRepository persistentTokenRepository(){
//        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
//        jdbcTokenRepository.setDataSource(dataSource);
//        return jdbcTokenRepository;
//    }
//
    @Bean
    AuthenticationProvider provider(){
        MyAuthenticationProvide provider = new MyAuthenticationProvide();
        provider.setUserDetailsService(userDetailService);
        provider.setPasswordEncoder(password());
        return provider;
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return new ProviderManager(Collections.singletonList(provider()));
    }

    //重写认证方法想 声明userDetailService实现 并设置加密的方式
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(password());
    }
    //设置密码加密方式
    @Bean
    PasswordEncoder password(){
        return new BCryptPasswordEncoder();
    }

    //重写方法 设置登录页面不需要认证就可以访问
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.addFilterAt(customAuthenticationFilter(),UsernamePasswordAuthenticationFilter.class);


        //登录配置
        http.cors().
                and().formLogin().loginPage("http://localhost:8080/login")//自定义自己编写的登录页面
                .and().authorizeRequests()
                .antMatchers("/getCode","/login**","/").permitAll()//设置什么路径不需要认证
                .antMatchers("/user/**").hasRole("user0")//底层会变成ROLE_user
                .anyRequest().authenticated()
                .and()
                .csrf().disable();

        //退出登录配置
        http.logout().logoutUrl("/logout")
                .logoutSuccessUrl("/test/hello").permitAll();

    }

    //注册自定义的UsernamePasswordAuthenticationFilter
    @Bean
    MyUsernamePasswordFilter customAuthenticationFilter() throws Exception {
        MyUsernamePasswordFilter filter = new MyUsernamePasswordFilter();
        filter.setFilterProcessesUrl("/login/post");
 filter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().write("login success");
            }

        });
                filter.setAuthenticationFailureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                        response.setContentType("text/html;charset=utf-8");
                        response.getWriter().write("login fail");
                    }
                });
        //这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

}
