package com.flipkart.restcontroller;
import com.flipkart.bean.Customer;
import com.flipkart.bean.Gym;
import com.flipkart.bean.User;
import com.flipkart.bean.Slots;
import com.flipkart.bean.User;
import com.flipkart.business.CustomerService;
//import org.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.ResponseCache;
import java.util.List;

@Path("/customer")
@Produces(MediaType.APPLICATION_JSON)
public class CustomerController {
    CustomerService customer_service=new CustomerService();
    String cur_customer_email="";
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


    @GET
    @Path("/login")
    public Response login(@QueryParam("username") String user_name, @QueryParam("password") String password) {
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
//    @GET
//    @Path("/viewAllGyms")
//    public Response viewGyms() {
//        if (cur_customer_email.equals("")){
//            return Response.ok("Please Log in to view all Gyms").build();
//        }
//        List<Gym> gymList = customer_service.getAllGymsWithSlots();
//        List<JSONObject> gymListJson=new List<JSONObject>();
//        for(Gym g:gymList){
//            JSONObject obj=new JSONObject();
////                     System.out.println("Gym name: " + gym.getGymName() +
////                    "\nGym ID: " + gym.getGymId() +
////                    "\nGym Location: " + gym.getLocation() +
////                    "\nGym Address: " + gym.getGymAddress() +
////                    "\nSlot List:");
////
////
//            obj.put("Gym Name ",g.getGymName());
//            obj.put("Gym Id ", g.getGymId());
//            obj.put("Gym Location ",g.getLocation());
//            obj.put("Gym Address ",g.getGymAddress());
//            gymListJson.add(obj);
//
//        }
//
//        return Response.ok(gymListJson).build();

//    }

}

