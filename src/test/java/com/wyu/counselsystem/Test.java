package com.wyu.counselsystem;

import com.wyu.counselsystem.util.JwtHelper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author afglow
 * @Date Create in 2023-02-2023/2/8-22:10
 * @Description
 */
public class Test {
    @org.junit.jupiter.api.Test
    public void test(){
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_User"));
        authorities.add(new SimpleGrantedAuthority("ROLE_admin"));
        List<String> s = new ArrayList<>();
        for(GrantedAuthority g:authorities){
            s.add(g.toString());
        }
        String yes = JwtHelper.createToken("yes", authorities);


    }


}
