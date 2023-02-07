package com.wyu.counselsystem.config;

import com.wyu.counselsystem.security.*;
import com.wyu.counselsystem.service.impl.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;
import java.util.UUID;

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



    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    //重写认证方法想 声明userDetailService实现 并设置加密的方式
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(password());
    }

    //设置密码加密方式
    @Bean
    PasswordEncoder password() {
        return new BCryptPasswordEncoder();
    }

    //重写方法 设置登录页面不需要认证就可以访问
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //自己重写的密码验证过滤器替换默认的
        http.addFilterAt(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);


        //登录配置
        http.cors().
                and().formLogin().loginPage("http://localhost:8080/login")//自定义自己编写的登录页面
                .and().authorizeRequests()
                .antMatchers("/getCode", "/login**", "/register").permitAll()//设置什么路径不需要认证
                .antMatchers("/user/**").hasRole("user")//底层会变成ROLE_user
                .antMatchers("admin/**").hasRole("admin")
                .anyRequest().authenticated()
                .and()
                .csrf().disable();

        //退出登录配置
        http.logout().logoutUrl("/logout")
                .logoutSuccessUrl("http://localhost:8080/login").permitAll();
        //401(未登录) 403(无权限)
        http.exceptionHandling().accessDeniedHandler(new DeniedHandler())
                .authenticationEntryPoint(new EntryPointHandler());
    }

    //注册自定义的UsernamePasswordAuthenticationFilter
    @Bean
    MyUsernamePasswordFilter customAuthenticationFilter() throws Exception {
        MyUsernamePasswordFilter filter = new MyUsernamePasswordFilter();
        filter.setFilterProcessesUrl("/login/post");
        filter.setAuthenticationSuccessHandler(new SuccessHandler());
        filter.setAuthenticationFailureHandler(new FailureHandler());
        //这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }


}
