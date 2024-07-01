package com.flipkart.restcontroller;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class UserController {


    @GET
    @Path("/")
    public Response getUserMenu() {

        return Response.ok("Welcome to Flipfit! \n" +
                        "1. Register -> /register\n" +
                        "2. Login -> /login\n" +
                        "3. Change Password -> /change_password")
                .build();

    }

    @GET
    @Path("/login")
    public Response login() {

        return Response.ok("To Login as - ! \n" +
                        "1. Admin -> /admin/login&username=\"\"&password=\"\"\n" +
                        "2. Customer -> /customer/login&username=\"\"&password=\"\"\n" +
                        "3. Gymowner -> /gymowner/login&username=\"\"&password=\"\"")
                .build();
    }

    @GET
    @Path("/register")
    public Response register() {
        return Response.ok("To Register as - ! \n" +
                        "1. Admin -> /admin/register" +
                        "2. Customer -> /customer/register" +
                        "3. Gymowner -> /gymowner/register")
                .build();
    }


}
