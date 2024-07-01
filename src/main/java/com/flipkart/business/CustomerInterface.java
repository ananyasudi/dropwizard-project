package com.flipkart.business;

import com.flipkart.bean.Bookings;
import com.flipkart.bean.Gym;
import com.flipkart.bean.User;

import java.util.List;

public interface CustomerInterface {

	boolean cancelSlots(int slotId);

	List<Bookings> getAllBookings(String userId);

	List<Gym> getAllGymsWithSlots();

	List<Gym> getAllGymsByArea(String area);

	boolean bookSlots(int gymId, int time, String email);

	boolean validateUser(String username, String pass);

	boolean createUser(User user);

	boolean verifyGymUserPassword(String email, String password, String updatedPassword);

	void updateGymUserPassword(String email, String password, String updatedPassword);
}
