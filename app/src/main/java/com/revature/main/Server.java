package com.revature.main;

import com.revature.controller.AuthenticationController;
import com.revature.controller.Controller;
import com.revature.controller.TicketController;
import com.revature.controller.UserController;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {

    public static final Logger logger = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) {
        Javalin app = Javalin.create(
                javalinConfig -> javalinConfig.addStaticFiles("static", Location.CLASSPATH));

        app.before(ctx -> logger.info("{} request received for {}", ctx.method(), ctx.path()));

        app.after(ctx -> logger.info("Server response: ({}) {}", ctx.status(), ctx.resultString()));

        map(app, new AuthenticationController(), new TicketController(), new UserController());
        app.start();
    }

    public static void map(Javalin app, Controller... controllers) {
        for (Controller c: controllers) {
            c.mapEndpoints(app);
        }
    }
}
