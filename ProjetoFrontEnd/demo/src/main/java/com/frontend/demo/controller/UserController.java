package com.frontend.demo.controller;

import com.frontend.demo.back_end.UserAPI;
import com.frontend.demo.exception.InvalidAcess;
import com.frontend.demo.exception.InvalidRequest;
import com.frontend.demo.exception.InvalidToken;
import com.frontend.demo.model.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    private static String getTokenCookies(HttpServletRequest request) throws InvalidToken {
        String res = request.getCookies() != null ? Arrays.stream(request.getCookies()).filter(c -> c.getName().equals("token")).findFirst().orElse(new Cookie("invalid","invalid")).getValue() : "invalid";
        if(res.equals("invalid"))
            throw new InvalidToken("");
        return res;
    }
    @GetMapping("/login/")
    public String getLoginPage(HttpSession session) {
        session.setAttribute("user", new User("","",""));
        return "login";
    }

    @PostMapping("/login/")
    public String postLogin(HttpServletResponse response, @ModelAttribute User user) {
        String token = UserAPI.login(user);
        String res = "login";
        System.out.println(token);
        if(!token.isEmpty())
        {
            Cookie cookie = new Cookie("token",token);
            cookie.setMaxAge(3600);
            cookie.setPath("/");
            response.addCookie(cookie);
            res = "redirect:/users/";
        }
        return res;
    }
    @GetMapping("/users/")
    public String getUsersPage(HttpSession session, HttpServletRequest request) throws InvalidRequest, InvalidToken {
        List<User> users = UserAPI.getUsers(getTokenCookies(request));
        session.setAttribute("users", users);
        return "users";
    }
    @GetMapping("/users/{id}")
    public String getUserPage(HttpSession session, @PathVariable String id, HttpServletRequest request) throws InvalidRequest, InvalidToken {
        User user = UserAPI.getUser(id,getTokenCookies(request));
        session.setAttribute("user", user);
        return "user";
    }
    @GetMapping("/users/update/{id}")
    public String getUpdateUserPage(HttpSession session, @PathVariable String id, HttpServletRequest request) throws InvalidRequest, InvalidToken, InvalidAcess {
        String token = getTokenCookies(request);
        User user = UserAPI.getUser(id,token);
        UserAPI.validateTokenUsernamePedido(token,user.getId());
        session.setAttribute("user", user);
        session.setAttribute("action", "/users/update/"+id);
        session.setAttribute("update",true);
        session.setAttribute("header", "Update User " + id);
        return "updateuser";
    }

    @PostMapping("/users/update/{id}")
    public String postUpdateUser(HttpSession session, @ModelAttribute User user, HttpServletRequest request) throws InvalidRequest, InvalidToken {
        UserAPI.updateUser(user,getTokenCookies(request));
        return "redirect:/users/"+user.getId();
    }


    @GetMapping("/users/create/")
    public String getCreateUserPage(HttpSession session, HttpServletRequest request) throws InvalidToken {
        UserAPI.validateTokenPedido(getTokenCookies(request));
        session.setAttribute("user", new User("","",""));
        session.setAttribute("action", "/users/create/");
        session.setAttribute("header", "Create User");
        session.setAttribute("update",false);
        return "updateuser";
    }

    @PostMapping("/users/create/")
    public String postCreateUser(HttpSession session, @ModelAttribute User user, HttpServletRequest request) throws InvalidRequest, InvalidToken {
        UserAPI.insertUser(user,getTokenCookies(request));
        return "redirect:/users/"+user.getId();
    }
    @GetMapping("/users/delete/{id}")
    public String getDeleteUserPage(HttpSession session, @PathVariable String id, HttpServletRequest request) throws InvalidRequest, InvalidToken, InvalidAcess {
        String token = getTokenCookies(request);
        User user = UserAPI.getUser(id,token);
        UserAPI.validateTokenUsernamePedido(token,user.getId());
        session.setAttribute("user", user);
        return "deleteuser";
    }


    @PostMapping("/users/delete/{id}")
    public String postDeleteUser(HttpSession session,@PathVariable String id, HttpServletRequest request) throws InvalidRequest, InvalidToken {
        UserAPI.deleteUser(id,getTokenCookies(request));
        return "redirect:/users/";
    }


    @ExceptionHandler
    public String exceptionHandler1(InvalidToken e) {
        return "redirect:/login/";
    }
    @ExceptionHandler
    public String exceptionHandler2(InvalidRequest e,HttpSession session) {
        session.setAttribute("message", e.getMessage());
        return "error";
    }

    @ExceptionHandler
    public String exceptionHandler3(InvalidAcess e,HttpSession session) {
        session.setAttribute("message", e.getMessage());
        return "error";
    }
}
