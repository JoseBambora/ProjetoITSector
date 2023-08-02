package com.frontend.demo.back_end;
import com.frontend.demo.exception.InvalidAcess;
import com.frontend.demo.exception.InvalidRequest;
import com.frontend.demo.exception.InvalidToken;
import com.frontend.demo.model.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.frontend.demo.util.ResponseMessage;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserAPI {
    private static final String api = "http://localhost:8080/";
    private static final RestTemplate rt = new RestTemplate();

    private static String insertToken(String link,String token)
    {
        return api + link + "?token="+token;
    }
    private static User convertJson2Java(Object objeto)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        User user = null;
        try {
            user = objectMapper.convertValue(objeto, User.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
    private static ResponseMessage getPedido(String link)
    {
        return rt.getForObject(link,ResponseMessage.class);
    }
    private static ResponseMessage postPedido(String link, User objeto)
    {
        return rt.postForObject(link,objeto,ResponseMessage.class);
    }
    private static ResponseMessage deletePedido(String link)
    {
        return rt.exchange(link,HttpMethod.DELETE,null,ResponseMessage.class).getBody();
    }
    private static ResponseMessage putPedido(String link, User objeto)
    {
        HttpEntity<User> requestEntity = new HttpEntity<>(objeto);
        return rt.exchange(link,HttpMethod.PUT,requestEntity,ResponseMessage.class).getBody();
    }
    private static User responseUser(ResponseMessage rm) throws InvalidToken, InvalidRequest {
        User res;
        if(rm.isValidToken())
            if(rm.isCorrectResponse())
                res = convertJson2Java(rm.getResponse());
            else
                throw new InvalidRequest((String) rm.getResponse());
        else
            throw new InvalidToken((String) rm.getResponse());
        return res;
    }
    public static List<User> getUsers(String token) throws InvalidRequest, InvalidToken {
        String link = insertToken("users/",token);
        ResponseMessage rm = getPedido(link);
        List<User> res = null;
        if(rm.isValidToken())
        {
            if(rm.isCorrectResponse())
            {
                List<Object> lista = (List<Object>) rm.getResponse();
                res = lista.stream().map(UserAPI::convertJson2Java).toList();
            }
            else
                throw new InvalidRequest((String) rm.getResponse());
        }
        else
            throw new InvalidToken((String) rm.getResponse());
        return res;
    }

    public static User getUser(String id,String token) throws InvalidRequest, InvalidToken {
        String link = insertToken("users/" + id,token);
        ResponseMessage rm = getPedido(link);
        return responseUser(rm);
    }
    public static String login(User user)
    {
        String link = api + "login/";
        ResponseMessage rm = postPedido(link,user);
        String res = "";
        if(rm.isValidToken())
        {
            res = (String) rm.getResponse();
        }
        return res;
    }
    public static User updateUser(User user,String token) throws InvalidRequest, InvalidToken {
        String link = insertToken("/users/",token);
        ResponseMessage rm = putPedido(link,user);
        return responseUser(rm);
    }
    public static User insertUser(User user, String token) throws InvalidRequest, InvalidToken {
        String link = insertToken("/users/",token);
        ResponseMessage rm = postPedido(link,user);
        return responseUser(rm);
    }
    public static User deleteUser(String id, String token) throws InvalidRequest, InvalidToken {
        String link = insertToken("/users/"+id,token);
        ResponseMessage rm = deletePedido(link);
        return responseUser(rm);
    }
    public static boolean validateTokenPedido(String token) throws InvalidToken {
        String link = insertToken("/token/",token);
        ResponseMessage rm = getPedido(link);
        if(rm.isValidToken())
            return true;
        else
            throw new InvalidToken((String) rm.getResponse());
    }

    public static boolean validateTokenUsernamePedido(String token, String id) throws InvalidToken, InvalidAcess {
        String link = insertToken("/token/"+id,token);
        ResponseMessage rm = getPedido(link);
        if(rm.isValidToken() && rm.isCorrectResponse())
            return true;
        else if(!rm.isValidToken())
            throw new InvalidToken((String) rm.getResponse());
        else
            throw new InvalidAcess((String) rm.getResponse());
    }
}

