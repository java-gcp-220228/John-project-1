package com.revature.controller;

import com.revature.model.Ticket;
import com.revature.service.TicketService;
import io.javalin.Javalin;
import io.javalin.http.Handler;

import java.util.List;

public class TicketController implements Controller {

    private TicketService ticketService;

    public TicketController() {
        this.ticketService = new TicketService();
    }

    private final Handler getAllTickets = ctx -> {
        List<Ticket> tickets = ticketService.getAllTickets();
        ctx.json(tickets);
    };

    @Override
    public void mapEndpoints(Javalin app) {
        app.get("/tickets", getAllTickets);
    }
}
