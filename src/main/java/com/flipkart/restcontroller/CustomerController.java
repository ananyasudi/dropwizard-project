package com.flipkart.restcontroller;
import com.flipkart.bean.*;
import com.flipkart.business.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/customer")
@Produces(MediaType.APPLICATION_JSON)
public class CustomerController {
    CustomerService customer_service=new CustomerService();
    String cur_customer_email="";

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response register(@FormParam("userName") String userName,
                             @FormParam("email") String email,
                             @FormParam("password") String password,
                             @FormParam("phoneNumber") String phoneNumber,
                             @FormParam("Address") String Address,
                             @FormParam("Location") String Location) {
        User user = new User();
        user.setEmail(email);
        user.setAddress(Address);
        user.setLocation(Location);
        user.setPassword(password);
        user.setUserName(userName);
        user.setPhoneNumber(phoneNumber);
        boolean status=false;

        status = customer_service.createUser(user);


        if(status){
            return Response.ok("Registered Customer Successfully ").build();

        }else{
            return Response.ok("Registration UnSuccessful").build();


        }
    }
//    Currentuser.setEmail("--");

//    List<Gym> viewAllGymswithSlots() {
//        System.out.println("List of Gyms");
//        List<Gym> gymList = customer_service.getAllGymsWithSlots();
//        return gymList;
//    }
//
//    private void printGyms(List<Gym> y) {
//        for (Gym gym : y) {
//            System.out.println();
//            System.out.println("Gym name: " + gym.getGymName() +
//                    "\nGym ID: " + gym.getGymId() +
//                    "\nGym Location: " + gym.getLocation() +
//                    "\nGym Address: " + gym.getGymAddress() +
//                    "\nSlot List:");
//
//            for (Slots slot : gym.getSlots()) {
//                String startTime = String.format("%02d:00", slot.getStartTime());
//                String endTime = String.format("%02d:00", (slot.getStartTime() + 1));
//                System.out.println("Start Time: " + startTime + " | End Time: " + endTime + " | Remaining Seats: " + slot.getSeatCount());
//            }
//
//        }
//    }

//
//    @GET
//    @Path("/login")
//    public Response login(@QueryParam("username") String user_name, @QueryParam("password") String password) {
//        System.out.println(user_name);
//        boolean validation=customer_service.validateUser(user_name,password);
//        if (validation) {
//            cur_customer_email=user_name; //using user_name for email because, the input asked is email but stored in user_name
//            // Customer Menu
//            String customerMenu="\n------------------CUSTOMER MENU-----------------\n" +
//                    "1. View all Gyms with slots -> /customer/viewAllGyms\n" +
//                    "2. Book Slot -> /customer/bookSlot\n" +
//                    "3. Cancel Slot -> /cutomer/cancelSlot\n" +
//                    "4. View all Bookings -> /customer/viewMyBookings\n" +
//                    "5. View all Gyms by Area -> /customer/viewGymsByArea\n" +
//                    "6. Logout -> /customer/logout";
//
//            return Response.ok(customerMenu).build();
//        }else{
//            return Response.ok("Invalid Credentials").build();
//        }
//    }


    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response login(@FormParam("user_name") String user_name, @FormParam("password") String password) {
        System.out.println(user_name);
        boolean validation=customer_service.validateUser(user_name,password);
        if (validation) {
            cur_customer_email=user_name; //using user_name for email because, the input asked is email but stored in user_name
            // Customer Menu
            String customerMenu="\n------------------CUSTOMER MENU-----------------\n" +
                    "1. View all Gyms with slots -> /customer/viewAllGyms\n" +
                    "2. Book Slot -> /customer/bookSlot\n" +
                    "3. Cancel Slot -> /cutomer/cancelSlot\n" +
                    "4. View all Bookings -> /customer/viewMyBookings\n" +
                    "5. View all Gyms by Area -> /customer/viewGymsByArea\n" +
                    "6. Logout -> /customer/logout";

            return Response.ok(customerMenu).build();
        }else{
            return Response.ok("Invalid Credentials").build();
        }
    }



    @GET
    @Path("/viewAllGyms")
    public Response viewAllGyms() {
        if (cur_customer_email.equals("")){
            return Response.ok("Please Log in to view all Gyms").build();
        }
        List<Gym> gymList = customer_service.getAllGymsWithSlots();
        ObjectMapper mapper = new ObjectMapper();
//        return Response.ok(mapper.writeValueAsString(gymList)).build();
        try {
            // Convert list of Gym objects to JSON string
            String json = mapper.writeValueAsString(gymList);
            // Return response with JSON string
            return Response.ok(json).build();
        } catch (Exception e) {
            // Handle exception appropriately, e.g., log it
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error converting to JSON").build();
        }

    }

    @GET
    @Path("/bookSlot")
    public Response bookSlot(@QueryParam("gymId") String gym_ID, @QueryParam("time") String time) {
        if (cur_customer_email.equals("")){
            return Response.ok("Please Log in to book a slot").build();
        }
        boolean status = customer_service.bookSlots(Integer.parseInt(gym_ID), Integer.parseInt(time), cur_customer_email);
        if(status){
            return Response.ok("Booked Slot Successfully").build();
        }else{
            return Response.ok("Booking UnSuccessful").build();

        }

    }

    @GET
    @Path("/viewBookings")
    public Response viewBookings() {
        if (cur_customer_email.equals("")){
            return Response.ok("Please Log in to book a slot").build();
        }
        List<Bookings> myBookings = customer_service.getAllBookings(cur_customer_email);
        System.out.println(myBookings);
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Convert list of Gym objects to JSON string
            String json = mapper.writeValueAsString(myBookings);
            // Return response with JSON string
            return Response.ok(json).build();
        } catch (Exception e) {
            // Handle exception appropriately, e.g., log it
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error converting to JSON").build();
        }

    }

    @GET
    @Path("/cancelBooking")
    public Response cancelBooking(@QueryParam("bookingId") int bookingId) {
        System.out.println("Came to func");
        if (cur_customer_email.equals("")){
            return Response.ok("Please Log in to cancel a slot").build();
        }
        try {
            boolean status=customer_service.cancelSlots(bookingId);
            if(status){
                return Response.ok("Booking Cancelled").build();
            }else{
                return Response.ok("Unable to Cancel Booking").build();

            }

        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error in cancelling slot").build();


        }
    }

    @GET
    @Path("/viewGymsByArea")
    public Response viewGymsByArea(@QueryParam("location") String location) {

        if (cur_customer_email.equals("")){
            return Response.ok("Please Log in to view gyms in area").build();
        }
        List<Gym> gymList = customer_service.getAllGymsByArea(location);
        ObjectMapper mapper = new ObjectMapper();
//        return Response.ok(mapper.writeValueAsString(gymList)).build();
        try {
            // Convert list of Gym objects to JSON string
            String json = mapper.writeValueAsString(gymList);
            // Return response with JSON string
            return Response.ok(json).build();
        } catch (Exception e) {
            // Handle exception appropriately, e.g., log it
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error converting to JSON").build();
        }

    }

    @GET
    @Path("/logout")
    public Response logout() {

        if (cur_customer_email.equals("")){
            return Response.ok("Customer not logged in").build();
        }
        cur_customer_email="";
        return Response.ok("Logged Out!").build();


    }

}

