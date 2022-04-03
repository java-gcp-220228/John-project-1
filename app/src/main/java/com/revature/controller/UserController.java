package com.revature.controller;

import com.revature.dto.UserDTO;
import com.revature.service.UserService;
import io.javalin.Javalin;
import io.javalin.http.Handler;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import java.util.List;

public class UserController implements Controller {

    private UserService userService;

    public UserController() {
        this.userService = new UserService();
    }

    private final Handler getAllUsers = ctx -> {
        Jws<Claims> token = getAuthToken(ctx);
        throwIfNotManager(token);
        List<UserDTO> userDTOS = userService.getUsers();
        ctx.json(userDTOS);
    };

    @Override
    public void mapEndpoints(Javalin app) {
        app.get("/employees", getAllUsers);
    }
}
