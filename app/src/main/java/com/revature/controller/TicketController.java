package com.revature.controller;

import com.revature.dto.EmployeeAddTicketDTO;
import com.revature.dto.ResolveTicketDTO;
import com.revature.dto.UserDTO;
import com.revature.model.Ticket;
import com.revature.service.JWTService;
import com.revature.service.TicketService;
import io.javalin.Javalin;
import io.javalin.http.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import java.util.List;

public class TicketController implements Controller {

    private TicketService ticketService;
    private final JWTService jwtService;
    private static final  String AUTH = "Authorization";
    private static final  String ROLE = "user_role";
    private static final  String USER_ID = "user_id";
    private static final  String PRIVILEGES = "Insufficient privileges to access endpoint";

    public TicketController() {
        this.ticketService = new TicketService();
        this.jwtService = JWTService.getInstance();
    }

    private final Handler getAllTickets = ctx -> {
        Jws<Claims> token = getAuthToken(ctx);

        if (!token.getBody().get(ROLE).equals("MANAGER")) {
            throw new UnauthorizedResponse(PRIVILEGES);
        }

        List<Ticket> tickets = ticketService.getAllTickets();
        ctx.json(tickets);
    };

    private final Handler getEmployeeTickets = ctx -> {
        Jws<Claims> token = getAuthToken(ctx);

        if (!token.getBody().get(ROLE).equals("EMPLOYEE")) {
            throw new UnauthorizedResponse(PRIVILEGES);
        }
        if (ctx.pathParam("id").matches("\\d+")
                && token.getBody().get(USER_ID).toString().matches("\\d+")
                && ctx.pathParam("id").equalsIgnoreCase(token.getBody().get(USER_ID).toString())) {
            int id = Integer.parseInt(ctx.pathParam("id"));
            List<Ticket> ticketDTOList = ticketService.getEmployeeTickets(id);
            ctx.json(ticketDTOList);
        } else {
            throw new BadRequestResponse("Employee id does not match");
        }
    };

    private final Handler addEmployeeTicket = ctx -> {
        Jws<Claims> token = getAuthToken(ctx);

        if (!token.getBody().get(ROLE).equals("EMPLOYEE")) {
            throw new UnauthorizedResponse(PRIVILEGES);
        }
        if (ctx.pathParam("id").matches("\\d+")
                && token.getBody().get(USER_ID).toString().matches("\\d+")
                && ctx.pathParam("id").equalsIgnoreCase(token.getBody().get(USER_ID).toString())) {
            int id = Integer.parseInt(ctx.pathParam("id"));

            EmployeeAddTicketDTO newTicket = new EmployeeAddTicketDTO();
            newTicket.setAmount(ctx.formParamAsClass("ticket-amount", Double.class).get());
            newTicket.setDescription(ctx.formParam("ticket-description"));

            UserDTO author = new UserDTO();
            author.setId(id);
            author.setUsername(ctx.formParam("ticket-author-username"));
            author.setFirstName(ctx.formParam("ticket-author-first"));
            author.setLastName(ctx.formParam("ticket-author-last"));
            author.setEmail(ctx.formParam("ticket-author-email"));
            author.setUserRole(ctx.formParam("ticket-author-role"));
            newTicket.setAuthor(author);

            newTicket.setStatus(ctx.formParam("ticket-status"));
            newTicket.setType(ctx.formParam("ticket-type"));

            UploadedFile image = ctx.uploadedFile("image");
            EmployeeAddTicketDTO result = ticketService.addEmployeeTicket(newTicket, image);
            ctx.json(result);
        } else {
            throw new BadRequestResponse("Employee id does not match");
        }
    };

    private final Handler serveTicket = ctx -> {
        Jws<Claims> token = getAuthToken(ctx);

        if (!token.getBody().get(ROLE).equals("MANAGER")) {
            throw new UnauthorizedResponse(PRIVILEGES);
        }

        String ticket_id = ctx.pathParam("ticket_id");
        String status = ctx.body();
        UserDTO resolver = new UserDTO();
        resolver.setId(token.getBody().get(USER_ID, Integer.class));
        resolver.setUsername(token.getBody().get("username", String.class));
        resolver.setFirstName(token.getBody().get("firstName", String.class));
        resolver.setLastName(token.getBody().get("lastName", String.class));
        resolver.setEmail(token.getBody().get("email", String.class));
        resolver.setUserRole(token.getBody().get(ROLE, String.class));

        ResolveTicketDTO response = ticketService.resolveTicket(ticket_id, status, resolver);
        ctx.json(response);
    };

    private Jws<Claims> getAuthToken(Context ctx) {
        if (!ctx.headerMap().containsKey(AUTH)) throw new UnauthorizedResponse("Authorization token missing");
        String jwt = ctx.header(AUTH).split(" ")[1];
        return jwtService.parseJwt(jwt);
    }

    @Override
    public void mapEndpoints(Javalin app) {
        app.get("/tickets", getAllTickets);
        app.get("/employees/{id}/tickets", getEmployeeTickets);
        app.post("/employees/{id}/tickets", addEmployeeTicket);
        app.patch("/tickets/{ticket_id}", serveTicket);
    }
}
