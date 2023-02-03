package com.wyu.counselsystem.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.wyu.counselsystem.pojo.LoginForm;
import com.wyu.counselsystem.util.CreateVerifiCodeImage;
import com.wyu.counselsystem.util.Result;
import com.wyu.counselsystem.util.ResultCodeEnum;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

/**
 * @author afglow
 * @Date Create in 2023-01-2023/1/27-20:13
 * @Description
 */
//@RequestMapping("/sys")
@RestController
public class SystemController {
    @GetMapping("/getCode")
    public void getCode(HttpServletRequest req, HttpServletResponse resp){
        BufferedImage codeImage = CreateVerifiCodeImage.getVerifiCodeImage();
        String verifyCode =new String(CreateVerifiCodeImage.getVerifiCode());
        //放到请求域
        HttpSession session = req.getSession();
        session.setAttribute("VerifyCode",verifyCode);
        System.out.println(session);
        System.out.println(session.getAttribute("VerifyCode"));
        //传送验证码图片
        try {
            ImageIO.write(codeImage,"JPEG",resp.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

//    @PostMapping("/test")
//    public Result test(){
//        System.out.println("dsdsdsds");
//        return Result.build("dd",ResultCodeEnum.SUCCESS);
//    }

    //登录的
    @PostMapping("/login")
    public Result login(@RequestBody LoginForm loginForm,HttpServletRequest request){
        System.out.println("dasdsda");
        String verifyCode = (String) request.getSession().getAttribute("VerifyCode");
        System.out.println(loginForm.getUsername());
//        if (!verifyCode.equals(loginForm.getCode())){
//            return Result.build("验证码错误",ResultCodeEnum.CODE_ERROR);
//        }
//        System.out.println(loginForm.getPassword());
        return Result.ok();
    }

    @RequestMapping("/logintest")
    public Result loginTest(HttpServletRequest request){
        String verifyCode = (String) request.getSession().getAttribute("VerifyCode");
        System.out.println(request.getSession().getAttribute("VerifyCode"));
        return Result.ok(verifyCode);
    }

    @RequestMapping("/forbidden")
    public Result forbidden(){
        return Result.build("访问禁止，无权限！",ResultCodeEnum.NO_PERMIT);
    }
//
//    @PostMapping("/login")
//    public Result login(@RequestBody LoginForm loginForm, HttpServletRequest request){
//        HttpSession session = request.getSession();
//        String verifyCode = loginForm.getVerifyCode();
//        //验证码校验
//        if (verifyCode == null|| verifyCode .equals("")){
//            return Result.fail().message("验证码不能为空");
//        }
//        String sessionVerifyCode = session.getAttribute("VerifyCode").toString().toUpperCase();
//        if (!verifyCode.equals(sessionVerifyCode)){
//            return Result.fail().message("验证码错误");
//        }
//        //移除session里面的验证码
//        session.removeAttribute("VerifyCode");
//
//        //按照角色跳转页面
//        Map<String,Object> map=new LinkedHashMap<>();
//        switch (loginForm.getAuthType()){
//            case 0:
//                try {
//                    Admin admin = adminService.login(loginForm);
//                    if (null != admin) {
//                        // 用户的类型和用户id转换成一个密文,以token的名称向客户端反馈
//                        map.put("token", JwtHelper.createToken(admin.getAdId().longValue(), 0));
//                    }else{
//                        throw new RuntimeException("用户名或者密码有误");
//                    }
//                    return Result.ok(map);
//                } catch (RuntimeException e) {
//                    e.printStackTrace();
//                    return Result.fail().message(e.getMessage());
//                }
//            case 1:
//                try {
//                    Recruiters recruiter = recruitersService.login(loginForm);
//                    if (null != recruiter) {
//                        // 用户的类型和用户id转换成一个密文,以token的名称向客户端反馈
//                        map.put("token", JwtHelper.createToken(recruiter.getRId().longValue(), 1));
//                    }else{
//                        throw new RuntimeException("用户名或者密码有误");
//                    }
//                    return Result.ok(map);
//                } catch (RuntimeException e) {
//                    e.printStackTrace();
//                    return Result.fail().message(e.getMessage());
//                }
//            case 2:
//                try {
//                    Volunteer volunteer = volunteerService.login(loginForm);
//                    if (null != volunteer) {
//                        // 用户的类型和用户id转换成一个密文,以token的名称向客户端反馈
//                        map.put("token", JwtHelper.createToken(volunteer.getVId().longValue(), 2));
//                    }else{
//                        throw new RuntimeException("用户名或者密码有误");
//                    }
//                    return Result.ok(map);
//                } catch (RuntimeException e) {
//                    e.printStackTrace();
//                    return Result.fail().message(e.getMessage());
//                }
//        }
//        return Result.fail().message("查无此用户");
//    }
//
//    @GetMapping("/getInfo")
//    public Result getInfoByToken(@RequestHeader("token") String token){
//        boolean expiration = JwtHelper.isExpiration(token);
//        //检测token是不是过期了
//        if(expiration){
//            return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
//        }
//        Long userId = JwtHelper.getUserId(token);
//        Integer userType = JwtHelper.getUserType(token);
//        Map<String,Object> map = new HashMap<>();
//       return Result.ok();
//    }
}
