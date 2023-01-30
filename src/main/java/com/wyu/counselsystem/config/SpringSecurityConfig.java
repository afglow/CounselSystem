package com.wyu.counselsystem.config;

import com.wyu.counselsystem.service.impl.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * @author afglow
 * @Date Create in 2023-01-2023/1/29-14:51
 * @Description
 */
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailService userDetailService;

    //注入数据源
    @Autowired
    private DataSource dataSource;

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
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

        //自定义无权限403页面
        http.exceptionHandling().accessDeniedPage("/forbidden");

        //登录配置
        http.formLogin()//自定义自己编写的登录页面
                .loginProcessingUrl("/login")//登录访问的路径 具体的controller不用手写
                .defaultSuccessUrl("/success").permitAll()//登录成功的默认跳转路径
                .and().authorizeRequests()
                .antMatchers("/","/login**","/test","/getCode").permitAll()//设置什么路径不需要认证
                .antMatchers("/test/index").hasRole("user")//底层会变成ROLE_sale
                .anyRequest().authenticated()
                .and()
                .userDetailsService(userDetailService)
                .csrf().disable();

        //退出登录配置
        http.logout().logoutUrl("/logout")
                .logoutSuccessUrl("/test/hello").permitAll();

    }



}
