package com.backend.demo.controller;

import java.util.*;

import com.backend.demo.component.JWTToken;
import com.backend.demo.exception.InvalidAcess;
import com.backend.demo.exception.InvalidRequest;
import com.backend.demo.exception.InvalidToken;
import com.backend.demo.model.User;
import com.backend.demo.service.UserService;
import com.backend.demo.util.ResponseMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    JWTToken serviceTokens;
    @Autowired
    UserService service;
    private List<User> iterableToListUser(Iterable<User> iterator)
    {
        List<User> res = new ArrayList<>();
        iterator.forEach(res::add);
        return res;
    }
    private Map<String,String> readQS(String qs)
    {
        Map<String,String> res = new HashMap<>();
        if(qs != null)
            Arrays.stream(qs.split("&")).map(s -> s.split("=")).forEach(sqs -> res.put(sqs[0],sqs[1]));
        return res;
    }
    private String validateToken(HttpServletRequest request) throws InvalidToken {
        Map<String,String> qs = readQS(request.getQueryString());
        if(!(qs.containsKey("token") && serviceTokens.validateJwtToken(qs.get("token"))))
            throw new InvalidToken("Invalid Token");
        return qs.get("token");
    }
    private void validateUsername(String id, String token) throws InvalidAcess {
        System.out.println(id);
        System.out.println(serviceTokens.getIdToken(token));
        if(id.equals(serviceTokens.getIdToken(token)))
            return;
        else
            throw new InvalidAcess("You do not have acess to make this operation");
    }
    private void validadeFieldsUser(User user) throws InvalidRequest {
        if(user.getId() != null && user.getUsername() != null && user.getPassword() != null)
        {
            if(user.getId().length() > 0 && user.getUsername().length() > 0 && user.getPassword().length() > 0)
            {
                return;
            }
            else
                throw new InvalidRequest("Some fields are empty");
        }
        else
            throw new InvalidRequest("Request does not have the necessary fields");
    }
    @ExceptionHandler
    public ResponseMessage exceptionHandlerInvalidToken(InvalidToken e) {
        return new ResponseMessage(false,false,e.getMessage());
    }
    @ExceptionHandler
    public ResponseMessage exceptionHandlerInvalidRequest(InvalidRequest e) {
        return new ResponseMessage(true,false,e.getMessage());
    }
    @ExceptionHandler
    public ResponseMessage exceptionHandlerInvalidAcess(InvalidAcess e) {
        return new ResponseMessage(true,false,e.getMessage());
    }
    @PostMapping("/login/")
    public ResponseMessage login(@RequestBody User user)
    {
        String message;
        User finalUser = user;
        User userDB = iterableToListUser(service.findAll()).stream().filter(u -> u.correctInfo(finalUser.getUsername(), finalUser.getPassword())).findFirst().orElse(null);
        boolean sucess = userDB != null;
        if(sucess)
            message = serviceTokens.generateJwtToken(userDB.getUsername(), userDB.getId());
        else
            message = "Login falhado";
        return new ResponseMessage(sucess,true,message);
    }
    @GetMapping("/users/")
    public ResponseMessage getUsers(HttpServletRequest request) throws InvalidToken {
        validateToken(request);
        return new ResponseMessage(true,true, iterableToListUser(service.findAll()).stream().map(User::rmPassword).toList());
    }
    @GetMapping("/users/{id}")
    public ResponseMessage getUser(HttpServletRequest request, @PathVariable String id) throws InvalidToken, InvalidRequest {
        validateToken(request);
        if(service.existsById(id))
            return new ResponseMessage(true,true, service.findById(id).orElse(null));
        else
            throw new InvalidRequest(String.format("User %s does not exist",id));

    }
    @PostMapping("/users/")
    public ResponseMessage addUser(HttpServletRequest request, @RequestBody User user) throws InvalidRequest, InvalidToken, InvalidAcess {
        String token = validateToken(request);
        validadeFieldsUser(user);
        if(service.existsById(user.getId()))
            throw new InvalidRequest(String.format("User %s already exist",user.getId()));
        return new ResponseMessage(true,true, service.save(user));
    }
    @PutMapping("/users/")
    public ResponseMessage updateUser(HttpServletRequest request, @RequestBody User user) throws InvalidToken, InvalidRequest, InvalidAcess {
        String token = validateToken(request);
        validadeFieldsUser(user);
        validateUsername(user.getId(),token);
        if(!service.existsById(user.getId()))
            throw new InvalidRequest(String.format("User %s does not exist",user.getId()));
        return new ResponseMessage(true,true, service.save(user));
    }
    @DeleteMapping("/users/{id}")
    public ResponseMessage deleteUser(HttpServletRequest request, @PathVariable String id) throws InvalidToken, InvalidRequest, InvalidAcess {
        String token = validateToken(request);
        if(service.existsById(id))
        {
            User res = service.findById(id).get();
            validateUsername(res.getId(),token);
            service.deleteById(id);
            return new ResponseMessage(true,true, res);
        }
        else
            throw new InvalidRequest(String.format("User %s does not exist",id));
    }
    @GetMapping("/token/")
    public ResponseMessage validateTokenPedido(HttpServletRequest request) throws InvalidToken {
        validateToken(request);
        return new ResponseMessage(true,true, true);
    }

    @GetMapping("/token/{id}")
    public ResponseMessage validateTokenUsernamePedido(HttpServletRequest request,@PathVariable String id) throws InvalidToken, InvalidAcess {
        String token = validateToken(request);
        validateUsername(id,token);
        return new ResponseMessage(true,true, true);
    }
}
