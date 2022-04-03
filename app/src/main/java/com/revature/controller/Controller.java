package com.revature.controller;

import com.revature.service.JWTService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.UnauthorizedResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public interface Controller {

    JWTService jwtService = JWTService.getInstance();
    String AUTH = "Authorization";
    String ROLE = "user_role";
    String USER_ID = "user_id";
    String PRIVILEGES = "Insufficient privileges to access endpoint";

    default Jws<Claims> getAuthToken(Context ctx) {
        if (!ctx.headerMap().containsKey(AUTH)) throw new UnauthorizedResponse("Authorization token missing");
        String jwt = ctx.header(AUTH).split(" ")[1];
        return jwtService.parseJwt(jwt);
    }

    default void throwIfNotManager(Jws<Claims> token) {
        if (!token.getBody().get(ROLE).equals("MANAGER")) {
            throw new UnauthorizedResponse(PRIVILEGES);
        }
    }

    default void throwIfNotEmployee(Jws<Claims> token) {
        if (!token.getBody().get(ROLE).equals("EMPLOYEE")) {
            throw new UnauthorizedResponse(PRIVILEGES);
        }
    }

    void mapEndpoints(Javalin app);
}
