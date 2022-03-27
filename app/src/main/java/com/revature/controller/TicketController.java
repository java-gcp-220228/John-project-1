package com.revature.controller;

import com.revature.dto.EmployeeAddTicketDTO;
import com.revature.dto.ResolveTicketDTO;
import com.revature.dto.UserDTO;
import com.revature.model.Ticket;
import com.revature.service.JWTService;
import com.revature.service.TicketService;
import io.javalin.Javalin;
import io.javalin.core.validation.Validator;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Handler;
import io.javalin.http.UnauthorizedResponse;
import io.javalin.http.UploadedFile;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

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
                && token.getBody().get("user_id").toString().matches("\\d+")
                && ctx.pathParam("id").equalsIgnoreCase(token.getBody().get("user_id").toString())) {
            int id = Integer.parseInt(ctx.pathParam("id"));
            List<Ticket> ticketDTOList = ticketService.getEmployeeTickets(id);
            ctx.json(ticketDTOList);
        } else {
            throw new BadRequestResponse("Employee id does not match");
        }
    };

    private final Handler addEmployeeTicket = ctx -> {
        String jwt = ctx.header("Authorization").split(" ")[1];
        Jws<Claims> token = jwtService.parseJwt(jwt);

        if (!token.getBody().get("user_role").equals("EMPLOYEE")) {
            throw new UnauthorizedResponse("Insufficient privileges to access endpoint");
        }
        if (ctx.pathParam("id").matches("\\d+")
                && token.getBody().get("user_id").toString().matches("\\d+")
                && ctx.pathParam("id").equalsIgnoreCase(token.getBody().get("user_id").toString())) {
            int id = Integer.parseInt(ctx.pathParam("id"));
//            EmployeeAddTicketDTO newTicket = ctx.formParamAsClass("ticket", EmployeeAddTicketDTO.class)
//                    .check(ticket -> ticket.getAmount() > 0, "Invalid reimbursement amount")
//                    .check(ticket -> ticket.getAuthor().getId() == id, "Employee id does not match")
//                    .check(ticket -> ticket.getType().equalsIgnoreCase("LODGING") |
//                                    ticket.getType().equalsIgnoreCase("TRAVEL") |
//                                    ticket.getType().equalsIgnoreCase("FOOD") |
//                                    ticket.getType().equalsIgnoreCase("OTHER"),
//                            "Invalid reimbursement type")
//                    .get();
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
        String jwt = ctx.header("Authorization").split(" ")[1];

        Jws<Claims> token = jwtService.parseJwt(jwt);

        if (!token.getBody().get("user_role").equals("MANAGER")) {
            throw new UnauthorizedResponse("Insufficient privileges to access endpoint");
        }

        String ticket_id = ctx.pathParam("ticket_id");
        String status = ctx.body();
        UserDTO resolver = new UserDTO();
        resolver.setId(token.getBody().get("user_id", Integer.class));
        resolver.setUsername(token.getBody().get("username", String.class));
        resolver.setFirstName(token.getBody().get("firstName", String.class));
        resolver.setLastName(token.getBody().get("lastName", String.class));
        resolver.setEmail(token.getBody().get("email", String.class));
        resolver.setUserRole(token.getBody().get("user_role", String.class));

        ResolveTicketDTO response = ticketService.resolveTicket(ticket_id, status, resolver);
        ctx.json(response);
    };

    @Override
    public void mapEndpoints(Javalin app) {
        app.get("/tickets", getAllTickets);
        app.get("/employees/{id}/tickets", getEmployeeTickets);
        app.post("/employees/{id}/tickets", addEmployeeTicket);
        app.patch("/tickets/{ticket_id}", serveTicket);
    }
}
