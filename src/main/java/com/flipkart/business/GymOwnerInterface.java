package com.flipkart.business;

import com.flipkart.bean.Gym;
import com.flipkart.bean.GymOwner;

import java.util.List;

public interface GymOwnerInterface {

	void addGymWithSlots(Gym gym);

	List<Gym> viewMyGyms(String userId);

	boolean verifyGymOwnerPassword(String email, String password);

	boolean validateLogin(String email, String password);

	void createGymOwner(GymOwner gymOwner);

	void updateGymOwnerPassword(String email, String password, String updatedPassword);
}
