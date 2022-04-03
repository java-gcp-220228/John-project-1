package com.revature.controller;

import com.revature.dto.LoginDTO;
import com.revature.dto.UserDTO;
import com.revature.model.User;
import com.revature.service.UserService;
import io.javalin.Javalin;
import io.javalin.http.Handler;

public class AuthenticationController implements Controller{

    private UserService userService;
    public AuthenticationController() {
        this.userService = new UserService();
    }

    private final Handler login = ctx -> {
        LoginDTO credentials = ctx.bodyAsClass(LoginDTO.class);

        User user = this.userService.login(credentials.getUsername(), credentials.getPassword());

        String jwt = Controller.jwtService.createJWT(user);

        ctx.header("Token", jwt);

        ctx.json(new UserDTO(user.getId(), user.getUsername(),user.getFirstName(), user.getLastName(),
            user.getEmail(), user.getUserRole()));
    };

    @Override
    public void mapEndpoints(Javalin app) {
        app.post("/login", login);
    }
}
