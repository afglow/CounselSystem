package com.wyu.counselsystem.security;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author afglow
 * @Date Create in 2023-01-2023/1/30-7:30
 * @Description
 */


public class MyWebAuthenticationDetails extends WebAuthenticationDetails {
    private static final long serialVersionUID = 6975601077710753878L;

    private String username;

    private String password;

    private String code;

    private String sessionCodeValue;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSessionCodeValue() {
        return sessionCodeValue;
    }

    public void setSessionCodeValue(String sessionCodeValue) {
        this.sessionCodeValue = sessionCodeValue;
    }

    public MyWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        StringBuilder requestBody = new StringBuilder();
        try{
            String line = null;
           BufferedReader reader = request.getReader();

           while(null != (line = reader.readLine())){
               requestBody.append(line);
           }
        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println(requestBody);
    }
}
