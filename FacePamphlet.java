/* 
 * File: FacePamphlet.java
 * -----------------------
 * This program implements a basic social network management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

public class FacePamphlet extends Program 
					implements FacePamphletConstants {

	/**
	 * Initializes the control bar interactors and creates the database and
	 * canvas objects (and adds the canvas to the display).
	 */
	public void init() {
		
		/* Initialize the control bar interactors. */
		addControlBars();
		
		/* Create the database and canvas objects. */
		database = new FacePamphletDatabase();
		canvas = new FacePamphletCanvas();
		add(canvas);
		
    }
    
    /**
     * Adds the control bar interactors and associated ActionListeners.
     */
	private void addControlBars() {
		
    	addWestCtrlBar();
    	addNorthCtrlBar();
    	addActionListeners();
    	
	}


	/**
	 * Adds the north control bar interactors for profile creation, deletion,
	 * lookup and for file loading and saving.
	 */
	private void addNorthCtrlBar() {
		
		/* Initialize the text fields. */
		nrthTxFld = new JTextField(TEXT_FIELD_SIZE);
		fileTxFld = new JTextField(TEXT_FIELD_SIZE);
		
		/* Initialize the buttons. */
		JButton addProfBut = new JButton(ADD_PROFILE);
		JButton delProfBut = new JButton(DEL_PROFILE);
		JButton lookupProfBut = new JButton(LOOKUP_PROFILE);
		JButton loadBut = new JButton(LOAD_FILE);
		JButton saveBut = new JButton(SAVE_FILE);
		
		/* Add the text fields and buttons to the canvas, together with line
		 * spacing labels as dividers. */
		add(new JLabel("Name"),NORTH);
		add(nrthTxFld, NORTH);
		add(addProfBut, NORTH);
		add(delProfBut, NORTH);
		add(lookupProfBut, NORTH);
		add(new JLabel("   File"),NORTH);
		add(fileTxFld, NORTH);
		add(loadBut, NORTH);
		add(saveBut, NORTH);
		
	}


	/**
	 * Adds the west control bar interactors for changing the displayed profile's
	 * status, image and adding friends.
	 */
	private void addWestCtrlBar() {
		
		/* Initialize the change status text field and button, giving both the
		 * same action command so the user can either click or hit enter. */
		chngStatusTxFld = new JTextField(TEXT_FIELD_SIZE);
		chngStatusTxFld.setActionCommand(CHG_STATUS);
		JButton chngStatusBut = new JButton(CHG_STATUS);
		
		/* Initialize the change picture text field and button, giving both the
		 * same action command so the user can either click or hit enter. */
		chngPicTxFld = new JTextField(TEXT_FIELD_SIZE);
		chngPicTxFld.setActionCommand(CHG_PICTURE);
		JButton chngPicBut = new JButton(CHG_PICTURE);
		
		/* Initialize the add friend text field and button, giving both the
		 * same action command so the user can either click or hit enter. */
		addFriendTxFld = new JTextField(TEXT_FIELD_SIZE);
		addFriendTxFld.setActionCommand(ADD_FRIEND);
		JButton addFriendBut = new JButton(ADD_FRIEND);	
		
		/* Add ActionListeners for the text fields. */
		chngStatusTxFld.addActionListener(this);
		chngPicTxFld.addActionListener(this);
		addFriendTxFld.addActionListener(this);
		
		/* Add all the interactors to the canvas. */
		add(chngStatusTxFld, WEST);
		add(chngStatusBut, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT),WEST);
		add(chngPicTxFld, WEST);
		add(chngPicBut, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT),WEST);
		add(addFriendTxFld, WEST);
		add(addFriendBut, WEST);
		
	}


	/**
     * This method detects when the buttons or text fields are
     * clicked or used, and responds to these actions accordingly.
     */
    public void actionPerformed(ActionEvent e) {
    	
    	/* Retrieve the command from the interactor triggered 
    	 * by the user. */
    	String cmd = e.getActionCommand();
    	
    	/* Remove the current profile from the canvas, to allow a new
    	 * or updated profile to be drawn if required. */
    	canvas.removeAll();
    	
    	/* Load a network file if the user has entered a filename in
    	 * the file text field clicked the load button, overwriting the 
    	 * current database of profiles with those described in the network 
    	 * file. */
    	if (cmd.equals(LOAD_FILE)) {
    		if(!fileTxFld.getText().equals("")){
    			String filename = fileTxFld.getText();
    			loadFile(filename);
    		}
    	}
    	
    	/* Save a network file if the user has entered a filename in
    	 * the file text field and clicked the save button, overwriting the 
    	 * current database of profiles with those described in the network 
    	 * file. */
    	else if (cmd.equals(SAVE_FILE)) {
    		if(!fileTxFld.getText().equals("")){
    			String filename = fileTxFld.getText();
    			saveFile(filename);
    		}
    	}
    	
    	/* Adds a profile to the database with a name equal to the text in the 
    	 * north text field if the user has entered text here and clicked the 
    	 * add button. */
    	else if (cmd.equals(ADD_PROFILE)) {
    		if (!nrthTxFld.getText().equals("")){
    			String profileName = nrthTxFld.getText();
        		addProfile(profileName);
    		}
    	} 
    	
    	/* Deletes the profile in the database that matches the text entered in
    	 * the north text field (if there is a match), if the user has clicked
    	 * the delete button. */
    	else if (cmd.equals(DEL_PROFILE)) {
    		if(!nrthTxFld.getText().equals("")){
    			String profileName = nrthTxFld.getText();
        		delProfile(profileName);
    		}
    	} 
    	
    	/* Displays the profile if a match is found for the text entered in the
    	 * north text field. */
    	else if (cmd.equals(LOOKUP_PROFILE)) {
    		if(!nrthTxFld.getText().equals("")){
    			String profileName = nrthTxFld.getText();
        		lookupProfile(profileName);
    		}
    	} 
    	
    	/* Changes the status of the displayed profile (if one is displayed), to
    	 * the text entered in the change status text field. Re-displays the updated 
    	 * profile and prints an application message confirming the change. */
    	else if (cmd.equals(CHG_STATUS) && currentProfile != null) {
    		String status = chngStatusTxFld.getText();
    		currentProfile.setStatus(status);
    		canvas.displayProfile(currentProfile);
    		canvas.showMessage("Status updated to " + status);
    	} 
    	
    	/* Tries to change the picture of the currently displayed profile (if one is displayed)
    	 * to that given by the filename in the change picture text field. */
    	else if (cmd.equals(CHG_PICTURE) && currentProfile != null) {
    		String filename = chngPicTxFld.getText();
    		tryToSetImage(filename);
    	} 
    	
    	/* Tries to add a friend to the currently displayed profile (if one is displayed), with
    	 * the friend's name given by the text in the add friend text field. */
    	else if (cmd.equals(ADD_FRIEND) && currentProfile != null) {
    		String friendName = addFriendTxFld.getText();
    		tryToAddFriend(friendName);
    		
    	} 
    	
    	/* Shows an application message error for no profile being displayed (which must be the
    	 * case if all previous else if statements have returned false). */
    	else if (currentProfile == null) {
    		canvas.showMessage("No profile displayed, please lookup or add a profile " +
    				"and try again");
    	}
		
		
	}
	
    /**
     * Attempts to read in a network file from the filename passed in as a parameter, using the
     * FacePamphletDatabase class' readDataFile method. Shows an application message to confirm 
     * the success or failure of this attempt.
     */
    private void loadFile(String filename) {
    	try {
    		database.readDataFile(filename);
    		canvas.showMessage("Loaded file " + filename);
    		
    	} catch (IOException ex) {
    		canvas.showMessage("Unable to open file " + filename);
    		
    	}
	}
    
    
    /**
     * Attempts to save a network file to the filename passed in as a parameter, using the 
     * FacePamphletDatabase class' saveFile method. Shows an application message to confirm
     * the success or failure of this attempt.
     */
    private void saveFile(String filename) {
    	try {
    		database.saveDataFile(filename);
    		canvas.showMessage("Saved file " + filename);
    		
    	} catch (IOException ex) {
    		canvas.showMessage("Unable to save file " + filename);
    	}
	}

    
    /**
     * Attempts to add the friend given by the string passed in as a parameter to the
     * currently displayed profile.
     */
	private void tryToAddFriend(String friendName) {
		
		/* If the string passed in is a valid profile name, and was succesfully added as
		 * a friend (i.e. was not a friend already), reciprocate the friendship by getting
		 * the 'friend's' profile and adding the current profile as a friend. */
    	if (database.containsProfile(friendName) && currentProfile.addFriend(friendName)) {
			FacePamphletProfile friendProfile = database.getProfile(friendName);
			friendProfile.addFriend(currentProfile.getName());
			canvas.showMessage(friendName + " added as a friend");
		
		/* Show an application message if the String does not match a valid profile
		 * name in the database. */
		} else if (!database.containsProfile(friendName)) {
			canvas.showMessage(friendName + " does not exist");
		
		/* The only remaining case possible is that the String is a valid profile name,
		 * but that name already exists in the friend list of the current profile, so 
		 * show an application message stating this. */
		} else {
			canvas.showMessage(currentProfile.getName() + " already has " + friendName +
					" as a friend");
			
		}
    	
    	/* Re-display the current profile for any updates made above. */
    	canvas.displayProfile(currentProfile);
	}

	/**
	 * Attempts to set the current profile's image to that passed in as the String
	 * parameter. If succesful (i.e. the filename is not blank and the filename is valid)
	 * the picture is changed, an application message confirming the success is shown and
	 * the filename is stored in the profile as a string (to allow saving and loading of the
	 * network by text file). If the filename is not valid, an application error message is
	 * shown instead. In either case the currentProfile is re-drawn on the canvas.
	 */
	private void tryToSetImage(String filename) {
		try {
			if(!filename.equals("")){
				GImage image = new GImage(filename);
				currentProfile.setImage(image);
				currentProfile.setImageString(filename);
				canvas.showMessage("Picture updated");
			}
		} catch (ErrorException ex) {
			canvas.showMessage("Unable to open image file: " + filename);
		}
		canvas.displayProfile(currentProfile);
	}


	/**
	 * Attempts to lookup a profile using the String passed in as a parameter, setting
	 * the current profile to this and updating this on the canvas if the String matches
	 * a profile in the database. Displays an application message confirming the success or 
	 * failure of the lookup.
	 */
	private void lookupProfile(String profileName) {
    	if (database.containsProfile(profileName)) {
			currentProfile = database.getProfile(profileName);
			canvas.showMessage("Displaying " + profileName);
			canvas.displayProfile(currentProfile);
		} else {
			currentProfile = null;
			canvas.showMessage("A profile with the name " + profileName +
					" does not exist");
		}
		
	}


	/**
	 * Attempts to delete a profile given by the String passed in as a parameter, setting the 
	 * current profile to null afterwards. The deletion will succeed if the String matches a
	 * profile in the database. An application message is displayed confirming the success or
	 * failure of the deletion.
	 */
	private void delProfile(String profileName) {
    	if (database.containsProfile(profileName)) {
			database.deleteProfile(profileName);
			canvas.showMessage("Profile of " + profileName + " deleted");
		} else {
			canvas.showMessage("A profile with the name + profileName + " +
					" does not exist");
			println("Profile does not exist");
		}
		currentProfile = null;
	}


	/**
	 * Adds a new profile, with name equal to the String passed in as a parameter, provided
	 * the name does not already exist in the database. Sets the current profile to this new
	 * profile if so. If the profile already exists the current profile is set to that profile.
	 * The display is updated to show the relevant profile and an application message is 
	 * displayed either confirming the creation of the new profile or stating that the profile 
	 * already exists.
	 */
	private void addProfile(String profileName) {
    	FacePamphletProfile profile;
		if (!database.containsProfile(profileName)) {
			profile = new FacePamphletProfile(profileName);
			database.addProfile(profile);  
			canvas.showMessage("New profile created");
		} else {
			profile = database.getProfile(profileName);
			canvas.showMessage("A profile with the name " + 
					profileName + " already exists");
		}
		currentProfile = profile;
		canvas.displayProfile(currentProfile);
	}


	/* Private instance variables for text field interactors to allow access 
     * by actionPerformed method. */
    private JTextField nrthTxFld;
    private JTextField chngStatusTxFld;
    private JTextField chngPicTxFld;
    private JTextField addFriendTxFld;
    private JTextField fileTxFld;
    
    /*
     * Private instance variables for the database, currently displayed profile and canvas.
     */
    private FacePamphletDatabase database;
    private FacePamphletProfile currentProfile;
    private FacePamphletCanvas canvas;
}
