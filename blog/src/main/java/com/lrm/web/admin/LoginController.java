package com.lrm.web.admin;

import com.lrm.po.User;
import com.lrm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by limi on 2017/10/15.
 */
@Controller
@RequestMapping("/admin")
public class LoginController {


    @Autowired
    private UserService userService;
    private HttpServletRequest request;


    @GetMapping
    public String loginPage() {
        return "admin/login";
    }

    @RequestMapping("/login")
    public String login(@RequestParam(required = false,defaultValue = "test") String username,
                        @RequestParam(required = false,defaultValue = "test") String password,
                        HttpSession session,
                        RedirectAttributes attributes) {
        if(session.getAttribute("user")!=null)return "admin/index";
        User user = userService.checkUser(username, password);
        if (user != null) {
            user.setPassword(null);
            session.setAttribute("user",user);
            return "admin/index";
        } else {
            System.out.println("username is "+username);
            if(!username.equals("test")){
                attributes.addFlashAttribute("message", "用户名和密码错误");
            }

            return "redirect:/admin";
        }
    }

/**
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes) {
        User user = userService.checkUser(username, password);
        if (user != null) {
            user.setPassword(null);
            session.setAttribute("user",user);
            return "admin/index";
        }else if(session.getAttribute("user")!=null) return "admin/index";
        else {
            attributes.addFlashAttribute("message", "用户名和密码错误");
            return "redirect:/admin";
        }
    }
**/
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
