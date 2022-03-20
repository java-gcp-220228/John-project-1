package com.revature.controller;

import com.revature.dto.LoginDTO;
import com.revature.model.User;
import com.revature.service.JWTService;
import com.revature.service.UserService;
import io.javalin.Javalin;
import io.javalin.http.Handler;

public class AuthenticationController implements Controller{

    private UserService userService;
    private JWTService jwtService;

    public AuthenticationController() {
        this.userService = new UserService();
        this.jwtService = new JWTService();
    }

    private Handler login = (ctx) -> {
        LoginDTO credentials = ctx.bodyAsClass(LoginDTO.class);

        User user = this.userService.login(credentials.getUsername(), credentials.getPassword());

        String jwt = this.jwtService.createJWT(user);

        ctx.header("Token", jwt);

        ctx.json(user);
    };

    @Override
    public void mapEndpoints(Javalin app) {
        app.post("/login", login);
    }
}
