package com.revature.controller;

import com.revature.model.Ticket;
import com.revature.service.JWTService;
import com.revature.service.TicketService;
import io.javalin.Javalin;
import io.javalin.http.Handler;
import io.javalin.http.UnauthorizedResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import java.util.List;

public class TicketController implements Controller {

    private TicketService ticketService;
    private JWTService jwtService;

    public TicketController() {
        this.ticketService = new TicketService();
        this.jwtService = JWTService.getInstance();
    }

    private final Handler getAllTickets = ctx -> {
        String jwt = ctx.header("Authorization").split(" ")[1];

        Jws<Claims> token = jwtService.parseJwt(jwt);

        if (!token.getBody().get("user_role").equals("MANAGER")) {
            throw new UnauthorizedResponse("Insufficient privileges to access endpoint");
        }

        List<Ticket> tickets = ticketService.getAllTickets();
        ctx.json(tickets);
    };

    private final Handler getEmployeeTickets = ctx -> {
        String jwt = ctx.header("Authorization").split(" ")[1];
        Jws<Claims> token = jwtService.parseJwt(jwt);

        if (!token.getBody().get("user_role").equals("EMPLOYEE")) {
            throw new UnauthorizedResponse("Insufficient privileges to access endpoint");
        }
        if (ctx.pathParam("id").matches("\\d+")
                && token.getBody().get("user_id").toString().matches("\\d+")) {
            int id = Integer.parseInt(ctx.pathParam("id"));
            List<Ticket> ticketDTOList = ticketService.getEmployeeTickets(id);
            ctx.json(ticketDTOList);
        }
    };

    @Override
    public void mapEndpoints(Javalin app) {
        app.get("/tickets", getAllTickets);
        app.get("/employees/{id}/tickets", getEmployeeTickets);
    }
}
