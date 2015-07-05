/*
 * File: FacePamphletDatabase.java
 * -------------------------------
 * This class keeps track of the profiles of all users in the
 * FacePamphlet application.  Note that profile names are case
 * sensitive, so that "ALICE" and "alice" are NOT the same name.
 */

import java.io.*;
import java.util.*;

import acm.graphics.GImage;

public class FacePamphletDatabase implements FacePamphletConstants {

	/** 
	 * Constructor
	 * This method takes care of the initialization needed for 
	 * the database.
	 */
	public FacePamphletDatabase() {
		database = new HashMap<String, FacePamphletProfile>();
	}
	
	
	/** 
	 * This method adds the given profile to the database.  If the 
	 * name associated with the profile is the same as an existing 
	 * name in the database, the existing profile is replaced by 
	 * the new profile passed in.
	 */
	public void addProfile(FacePamphletProfile profile) {
		String key = profile.getName();
		database.put(key, profile);
	}

	
	/** 
	 * This method returns the profile associated with the given name 
	 * in the database.
	 */
	public FacePamphletProfile getProfile(String name) {
		FacePamphletProfile profile = database.get(name);
		return profile;
	}
	
	
	/** 
	 * This method removes the profile associated with the given name
	 * from the database.  It also updates the list of friends of all
	 * other profiles in the database to make sure that this name is
	 * removed from the list of friends of any other profile.
	 * 
	 * If there is no profile in the database with the given name, then
	 * the database is unchanged after calling this method.
	 */
	public void deleteProfile(String name) {
		/* Remove the profile from the database. */
		database.remove(name);
		
		/* Remove the profile name from the friend lists of any profiles
		 * which contain it. */
		for( String key: database.keySet() ) {
			Iterator<String> friendIterator = database.get(key).getFriends();
			while( friendIterator.hasNext() ) {
				if (friendIterator.next().equals(name)) {
					friendIterator.remove();
					break;
				}
			}
		}
	}

	
	/** 
	 * This method returns true if there is a profile in the database 
	 * that has the given name.  It returns false otherwise.
	 */
	public boolean containsProfile(String name) {
		if (database.containsKey(name)) {
			return true;
		} else {
			return false;
		}
	}
	
	
	/**
	 * Reads in a data (text) file, overwriting the current database with its contents.
	 */
	public void readDataFile(String filename) throws IOException {
		try {
			/* Create a BufferedReader object for the file with name given by the parameter. */
			BufferedReader rd = new BufferedReader(new FileReader(filename));		
			
			/* Clear the database of all existing profiles */
			database.clear();
			
			/* Read in the number of profiles given by first line of the file. */
			int numProfiles = Integer.parseInt(rd.readLine());
			
			/* Parse each profile from the text file and add it to the database. */
			for (int i = 0; i < numProfiles; i++) {
				
				/* Get the name, image filename and status for the next profile. */
				String name = rd.readLine();
				String imageName = rd.readLine();
				String status = rd.readLine();
				
				/* Create a profile object using the name above. */
				FacePamphletProfile profile = new FacePamphletProfile(name);
				
				/* If the image filename is not blank, set the profile's image to
				 * this file, and store the filename in the profile's imageString
				 * variable. */
				if (!imageName.equals("")) {
					profile.setImage(new GImage(imageName));
					profile.setImageString(imageName);
				}
				
				/* Sets the status of the profile. */
				if (!status.equals("")) {
					profile.setStatus(status);
				}
				
				/* Reads the remaining lines until the next line break, adding each
				 * line as a friend to the profile's friend list. */
				while (true) {
					String friend = rd.readLine();
					if (friend.equals("")) {
						break;
					} else {
						profile.addFriend(friend);	
					}
				}
				
				/* Add the newly created profile to the database. */
				database.put(name, profile);
			}
			
			rd.close();
			
		} catch (IOException ex) {
			/* This method throws the IOException, and so it is dealt with in the
			 * main FacePamphlet class' calling method. */
		}
	}


	/**
	 * Saves the current database to a text file, to allow loading later.
	 */
	public void saveDataFile(String filename) throws IOException {
		try {
			
			/* Create a PrintWriter object which will write a file to the filename
			 * given by the String parameter. */
			PrintWriter wr = new PrintWriter(new FileWriter(filename));
			
			/* Add the number of profiles to the first line in text file  */
			int numProfiles = database.size();
			wr.println(numProfiles);
			
			/* Get each profile from the database and store it in the text file. */
			for (String key: database.keySet()) {
				
				FacePamphletProfile profile = database.get(key);
				
				/* Add the name of the profile to the next line. */
				wr.println(profile.getName());
				
				/* Add the name of the profile's image to the next line. */
				wr.println(profile.getImageString());
				
				/* Add the status to the next line. */
				wr.println(profile.getStatus());
				
				/* Create an iterator to iterate over the profile's friend list. */
				Iterator<String> it = profile.getFriends();
				
				/* Add the name of each friend, line by line */
				while (it.hasNext()) {
					wr.println(it.next());
				}
				
				/* Add empty line to signal end of this profile */
				wr.println("");
			}
			
			wr.close();
			
		} catch (IOException ex) {
			/* This method throws the IOException, and so it is dealt with in the
			 * main FacePamphlet class' calling method. */
		}
	}
	
	/* Instance variable for the database. */
	private HashMap<String,FacePamphletProfile> database;





}
