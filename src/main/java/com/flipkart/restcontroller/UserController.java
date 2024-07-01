package com.flipkart.restcontroller;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
                        "1. Admin -> /admin/login?username=\"\"&password=\"\"\n" +
                        "2. Customer -> /customer/login?username=\"\"&password=\"\"\n" +
                        "3. GymOwner -> /gymOwner/login?email=\"\"&password=\"\"")
                .build();
    }

}
