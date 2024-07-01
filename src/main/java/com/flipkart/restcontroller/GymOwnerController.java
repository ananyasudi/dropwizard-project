package com.flipkart.restcontroller;

import com.flipkart.bean.Gym;
import com.flipkart.bean.Slots;
import com.flipkart.business.GymOwnerService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.ResponseCache;
import java.util.ArrayList;
import java.util.List;

@Path("/gymOwner")
@Produces(MediaType.APPLICATION_JSON)
public class GymOwnerController {
    GymOwnerService gymowner_service = new GymOwnerService();
    String cur_gymowner_email="";

    @GET
    @Path("/login")
    public Response login(@QueryParam("email") String email, @QueryParam("password") String password) {

//        System.out.println(email);
        boolean validation=gymowner_service.validateGymOwner(email,password);
        System.out.println("validation->" + validation);
        if (validation) {
            cur_gymowner_email=email; //using user_name for email because
            // GymOwner Menu
            String gymOwnerMenu="\n------------------GYM OWNER MENU-----------------\n" +
                    "1. Add new gym -> /gymOwner/addNewGym?gymName=\"\"&gymAddress=\"\"&location=\"\"&ownerId=\"\"\n" +
                    "2. Delete gym -> /gymOwner/viewGyms?gymOwnerId=\"\"\n" +
                    "3. View all gyms and gym slots -> /gymOwner/viewAllGymsAndSlots?gymOwnerId=\"\"\n" +
                    "4. Logout -> /gymOwner/logout";

            return Response.ok(gymOwnerMenu).build();
        }else{
            return Response.ok("Invalid Credentials").build();
        }
    }

    @GET
    @Path("/addNewGym")
    public Response addGym(@QueryParam("gymName") String gymName,
                           @QueryParam("gymAddress") String gymAddress,
                           @QueryParam("location") String location,
                           @QueryParam("ownerId") String ownerId) {

        Gym gym = new Gym();
        List<Slots> slotsList = new ArrayList<>();
        gym.setGymName(gymName);
        gym.setGymAddress(gymAddress);
        gym.setLocation(location);
        gym.setStatus("Inactive");
        gym.setOwnerId(ownerId);
        gym.setSlots(slotsList);
//        change the above to stream format

        gymowner_service.addGymWithSlots(gym);

        if (true) {
            return Response.ok("Gym added successfully").build();
        }else{
            return Response.ok("Gym addition failed.").build();
        }
    }

    @GET
    @Path("/viewGyms")
    public Response viewGyms(@QueryParam("gymOwnerId") String gymOwnerId) {

        System.out.println("here in deleteGym in GymOwnerController\nowner id -> " + gymOwnerId);

        List<Gym> listOfGyms = gymowner_service.viewAllGyms(gymOwnerId);

        if (!listOfGyms.isEmpty()) {
            StringBuilder gymsInfo = new StringBuilder();
            for (Gym gym : listOfGyms) {
                gymsInfo.append("Gym Id: ").append(gym.getGymId())
                        .append(", Gym Name: ").append(gym.getGymName())
                        .append(", Address: ").append(gym.getGymAddress())
                        .append(", Location: ").append(gym.getLocation())
                        .append(", Status: ").append(gym.getStatus())
                        .append("\n");
            }

            gymsInfo.append("\n\n -> /gymOwner/deleteGym?gymId=\"\"\n");

            return Response.ok("List of Gyms:\n" + gymsInfo.toString()).build();

        }
        else {
            return Response.ok("No gyms available or incorrect gymOwnerId.").build();
        }

    }

    @GET
    @Path("/deleteGym")
    public Response deleteGym(@QueryParam("gymId") String gymId) {

        boolean flag = gymowner_service.deleteGymById(Integer.parseInt(gymId));

        if (flag) {
            return Response.ok("Gym with gym id: " + gymId + " deleted successfully. ").build();

        }
        else {
            return Response.ok("No gyms available or incorrect gymOwnerId.").build();
        }

    }

    @GET
    @Path("/viewAllGymsAndSlots")
    public Response viewAllGymsAndSlots(@QueryParam("gymOwnerId") String gymOwnerId) {

        System.out.println("here in viewAllGymsAndSlots in GymOwnerController\nowner id -> " + gymOwnerId);

        List<Gym> resList = gymowner_service.viewMyGyms(gymOwnerId);

        if (!resList.isEmpty()) {
            StringBuilder gymsInfo = new StringBuilder();
            for (Gym gym : resList) {
                gymsInfo.append("Gym Id: ").append(gym.getGymId())
                        .append(", Gym Name: ").append(gym.getGymName())
                        .append(", Address: ").append(gym.getGymAddress())
                        .append(", Location: ").append(gym.getLocation())
                        .append(", Status: ").append(gym.getStatus())
                        .append("\nSlots: ");
                for (Slots slot : gym.getSlots()) {
                    gymsInfo.append(" Slot ID: ").append(slot.getSlotsId())
                            .append(", Slot Time: ").append(slot.getStartTime()).append(" - ").append(slot.getStartTime() + 1)
                            .append(", Seats: ").append(slot.getSeatCount())
                            .append("\n\n\n");
                }
            }

            return Response.ok("List of Gyms and Slots:\n" + gymsInfo.toString()).build();
        }
        else {
            return Response.ok("No gyms available or incorrect gymOwnerId.").build();
        }

    }

}