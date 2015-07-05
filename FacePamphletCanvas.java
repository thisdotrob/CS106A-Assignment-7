/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.
 */


import acm.graphics.*;

import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas 
					implements FacePamphletConstants {
	
	/** 
	 * Constructor: This method initializes the display.
	 */
	public FacePamphletCanvas() {
		
	}

	
	/** 
	 * This method displays a message string near the bottom of the 
	 * canvas.  Every time this method is called, the previously 
	 * displayed message (if any) is replaced by the new message text 
	 * passed in.
	 */
	public void showMessage(String msg) {
		
		appMessage.setLabel(msg);
		appMessage.setFont(MESSAGE_FONT);
		double x = ( getWidth() - appMessage.getWidth() ) / 2 ;
		double y = getHeight() - BOTTOM_MESSAGE_MARGIN;
		appMessage.setLocation(x, y);
		add(appMessage);
		
	}
	
	
	/** 
	 * This method displays the given profile on the canvas.  The 
	 * canvas is first cleared of all existing items (including 
	 * messages displayed near the bottom of the screen) and then the 
	 * given profile is displayed.  The profile display includes the 
	 * name of the user from the profile, the corresponding image 
	 * (or an indication that an image does not exist), the status of
	 * the user, and a list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		displayProfileName(profile.getName());
		displayImage(profile.getImage());
		displayStatus(profile.getStatus(),profile.getName());
		displayFriends(profile.getFriends());
	}
	
		
	/**
	 * Displays the profile name on the canvas.
	 */
	private void displayProfileName(String name) {
		profileNameLab = new GLabel(name);
		profileNameLab.setColor(Color.BLUE);
		profileNameLab.setFont(PROFILE_NAME_FONT);
		double x = LEFT_MARGIN;
		double y = TOP_MARGIN + profileNameLab.getAscent();
		profileNameLab.setLocation(x,y);
		add(profileNameLab);
	}
	
	
	/**
	 * Displays a profile's image if one exists, or a placeholder if one doesn't.
	 */
	private void displayImage(GImage image) {
		double x = LEFT_MARGIN;
		/* Set the Y coordinate of the image to be below the profile's name label
		 * with a margin given by the IMAGE_MARGIN constant. */
		double y = profileNameLab.getY() + IMAGE_MARGIN;		
		if (image != null) {
			drawImage(x,y,image);
		} else {
			drawImagePlaceHolder(x,y);
		}
	}
	
	
	/**
	 * Draws an image on the canvas, first scaling it in line with the 
	 * IMAGE_WIDTH and IMAGE_HEIGHT constants.
	 */
	private void drawImage(double x, double y, GImage image) {
		double sx = IMAGE_WIDTH / image.getWidth();
		double sy = IMAGE_HEIGHT / image.getHeight();
		image.scale(sx, sy);
		add(image,x,y);
	}


	/**
	 * Draws an image placeholder consisting of a black unfilled rectangle
	 * and a "No Image" label centered within.
	 */
	private void drawImagePlaceHolder(double x, double y) {
		GRect rect = new GRect(IMAGE_WIDTH,IMAGE_HEIGHT);
		rect.setColor(Color.BLACK);
		GLabel lab = new GLabel("No Image");
		lab.setFont(PROFILE_IMAGE_FONT);
		double labX = x + (( rect.getWidth() - lab.getWidth()) / 2 );
		double labY = y + ((rect.getHeight() + lab.getAscent()) / 2);
		add(rect,x,y);
		add(lab,labX,labY);
	}
	

	/**
	 * Displays a profile's status on the canvas, positioned below the image.
	 */
	private void displayStatus(String status, String name) {
		if (status.equals("")) {
			status = "No current status";
		} else {
			status = name + " is " + status;
		}
		GLabel statusLab = new GLabel(status);
		statusLab.setFont(PROFILE_STATUS_FONT);
		/* Compute the Y coordinate of the image. */
		double imageY = profileNameLab.getY() + IMAGE_MARGIN;
		/* Set the status label's Y coordinate to be below the image, with a
		 * margin given by the STATUS_MARGIN constant. */
		double y = imageY + IMAGE_HEIGHT + STATUS_MARGIN + statusLab.getAscent();
		add(statusLab,LEFT_MARGIN,y);		
	}
	
	
	/**
	 * Displays a profile's list of friends in the right of the canvas.
	 */
	private void displayFriends(Iterator<String> friends) {
		GLabel label = new GLabel("Friends:");
		label.setFont(PROFILE_FRIEND_LABEL_FONT);
		double x = getWidth() / 2;
		double y = profileNameLab.getY() + IMAGE_MARGIN;
		add(label,x,y);
		
		while(friends.hasNext()) {
			String friend = friends.next();
			GLabel friendLab = new GLabel(friend);
			friendLab.setFont(PROFILE_FRIEND_FONT);
			y += friendLab.getHeight();
			add(friendLab,x,y);			
		}
	}
	

	/* Private instance variable for the application message */
	GLabel appMessage = new GLabel("");
	
	/* 
	 * Private instance variable for the profile name label,
	 * to allow access to the Y coordinate for relative positioning of 
	 * the profile image and profile status.
	 */
	GLabel profileNameLab;
	
}
