import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.HashSet;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.ArrayList;
import java.io.*;
/**
 * A class to manage your contacts and meetings.
 */
public class ContactManagerImpl implements ContactManager, Serializable{
	private Set<Contact> contactSet = new HashSet<Contact>();
	private Set<MeetingImpl> meetingSet = new TreeSet<MeetingImpl>();  //Treeset is used to keep Meetings in chronological order by date/time
	private int uniqueContactIdGenerator = 1;
	private int uniqueMeetingIdGenerator = 1;

	public ContactManagerImpl(){
		File dataFile = new File("contacts.txt");
		ObjectInputStream in = null;
		if (dataFile.exists()){
			try{
				in = new ObjectInputStream(new FileInputStream(dataFile));
				contactSet = (HashSet) in.readObject();
				meetingSet = (TreeSet) in.readObject();
				in.close();
			}
			catch (IOException e){
				e.printStackTrace();
			}
			catch (ClassNotFoundException e){
				e.printStackTrace();
			}
			finally{
				try{
					if (in != null){
						in.close();
					}
				}
				catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Add a new meeting to be held in the future.
	 *
	 * @param contacts a list of contacts that will participate in the meeting
	 * @param date the date on which the meeting will take place
	 * @return the ID for the meeting
	 * @throws IllegalArgumentException if the meeting is set for a time in the past,
	 * or if any contact is unknown / non-existent
	 */
	public int addFutureMeeting(Set<Contact> contacts, Calendar date){
		if (! doContactsAllExist(contacts)){
			throw new IllegalArgumentException("ContactSet contains unknown contact");
		}
		if (new GregorianCalendar().getInstance().after(date)){     //checks if date is in the past
			throw new IllegalArgumentException("Date must be in future");
		}
		else{
			FutureMeetingImpl newFutureMeeting = new FutureMeetingImpl(contacts, date, uniqueMeetingIdGenerator);
			uniqueMeetingIdGenerator++;
			meetingSet.add(newFutureMeeting);
			flush();
			return newFutureMeeting.getId();
		}
	}

	/**
	* Checks that all the contacts in the Contact Set are known
	*
	* @param a set of contacts to check
	* @return a boolean. True if all the contacts exist. False if they don't
	*/
	private boolean doContactsAllExist(Set<Contact> contacts){
		for (Contact cont: contacts){
			if (!contactSet.contains(cont)){
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns the PAST meeting with the requested ID, or null if it there is none.
   	 *
	 * @param id the ID for the meeting
	 * @return the meeting with the requested ID, or null if it there is none.
	 * @throws IllegalArgumentException if there is a meeting with that ID happening in the future
	 */
	public PastMeeting getPastMeeting(int id){
		updateMeetingList();  //any meetings which were added as future meetings but have now taken place are converted into past meetings
		for (Meeting meeting: meetingSet){
			if (meeting.getId() == id){
				if (!isMeetingInPast(meeting)){
					throw new IllegalArgumentException("Meeting is not a past Meeting");
				}
				else{
					return (PastMeeting) meeting;
				}
			}
		}
		return null;
	}

	/*
	*
	* A method for determining whether a meeting has taken place in the past
	* @param the meeting to test
	* @return boolean value. True if the meeting took place in the past. False otherwise.
	*/
	private boolean isMeetingInPast(Meeting meeting){
		Calendar currentTime = Calendar.getInstance();
		return meeting.getDate().before(currentTime);
	}

	/**
	 * Returns the FUTURE meeting with the requested ID, or null if there is none.
	 *
	 * @param id the ID for the meeting
	 * @return the meeting with the requested ID, or null if it there is none.
	 * @throws IllegalArgumentException if there is a meeting with that ID happening in the past
	 */
	public FutureMeeting getFutureMeeting(int id){
		updateMeetingList(); //any meetings which were added as past meetings with addNewPastMeeting but had future dates are now changed into FutureMeetings
		for (Meeting meeting: meetingSet){
			if (meeting.getId() == id){
				if (isMeetingInPast(meeting)){
					throw new IllegalArgumentException("Meeting not in future");
				}
				else{
					return (FutureMeeting) meeting;
				}
			}
		}
		return null;

	}

	/**
	 * Returns the meeting with the requested ID, or null if it there is none.
	 *
	 * @param id the ID for the meeting
	 * @return the meeting with the requested ID, or null if it there is none.
	 */
	public Meeting getMeeting(int id){
		for (Meeting meeting: meetingSet){
			if (meeting.getId() == id){
					return (Meeting) meeting;
				}
		}
		return null;
	}

	/**
	 * Returns the list of future meetings scheduled with this contact.
	 *
	 * If there are none, the returned list will be empty. Otherwise,
	 * the list will be chronologically sorted and will not contain any
	 * duplicates.
	 *
	 * @param contact one of the user’s contacts
	 * @return the list of future meeting(s) scheduled with this contact (maybe empty).
	 * @throws IllegalArgumentException if the contact does not exist
	 */
	public List<Meeting> getFutureMeetingList(Contact contact){
		updateMeetingList(); ////Makes sure meetings in meetingSet have the correct class.
		if (!contactSet.contains(contact)){
			throw new IllegalArgumentException("Contact not found");
		}
		List<Meeting> meetingsWithContact = new ArrayList<Meeting>();
		for (Meeting meeting : meetingSet){
				if (meeting.getContacts().contains(contact) && (!isMeetingInPast(meeting))) {
						meetingsWithContact.add(meeting); //meetings already chronologically sorted in TreeSet, no need to sort the list here
				}
		}
		return meetingsWithContact;
	}

	/**
	 * Returns the list of meetings that are scheduled for, or that took
	 * place on, the specified date
	 *
	 * If there are none, the returned list will be empty. Otherwise,
	 * the list will be chronologically sorted and will not contain any
	 * duplicates.
	 *
	 * @param date the date
	 * @return the list of meetings
	 */
	public List<Meeting> getFutureMeetingList(Calendar date){
		updateMeetingList(); //Makes sure meetings in meetingSet have the correct class.
		List<Meeting> meetingsOnDate = new ArrayList<Meeting>();
		for (Meeting meeting : meetingSet){
			if (isSameDate(meeting.getDate(), date)){
				meetingsOnDate.add(meeting);
			}
		}
		return meetingsOnDate;
	}

	/**
	*
	* A method to determine whether two Calendar objects occur on the same date
	* @ param The first calendar object
	* @ param The second calendar object
	* @ return a boolean value. True if the two calendars occur on the same date. False otherwise.
	*
	*/
	private boolean isSameDate(Calendar date1, Calendar date2){
		if (date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR) && date1.get(Calendar.MONTH) == date2.get(Calendar.MONTH)
		&& date1.get(Calendar.DAY_OF_MONTH) == date2.get(Calendar.DAY_OF_MONTH)){
			return true;
		}
		return false;
	}

	/**
	 * Returns the list of past meetings in which this contact has participated.
	 *
	 * If there are none, the returned list will be empty. Otherwise,
	 * the list will be chronologically sorted and will not contain any
	 * duplicates.
	 *
	 * @param contact one of the user’s contacts
	 * @return the list of future meeting(s) scheduled with this contact (maybe empty).
	 * @throws IllegalArgumentException if the contact does not exist
	 */
	public List<PastMeeting> getPastMeetingList(Contact contact){
		updateMeetingList(); //Makes sure meetings in meetingSet have the correct class.
		List<PastMeeting> pastMeetingsWithContact = new ArrayList<PastMeeting>();
		if (!contactSet.contains(contact)){
			throw new IllegalArgumentException("Contact does not exist");
		}
		for (Meeting meeting : meetingSet){
			for (Contact contact2 : meeting.getContacts()){
				if (contact.equals(contact2) && isMeetingInPast(meeting)){
					pastMeetingsWithContact.add((PastMeeting)meeting);
				}
			}
		}
		return pastMeetingsWithContact;
	}

	/**
	 * Create a new record for a meeting that took place in the past.
	 *
	 * @param contacts a list of participants
	 * @param date the date on which the meeting took place
	 * @param text messages to be added about the meeting.
	 * @throws IllegalArgumentException if the list of contacts is
	 * empty, or any of the contacts does not exist
	 * @throws NullPointerException if any of the arguments is null
	 */
	public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text){
		if (contacts.isEmpty()){
			throw new IllegalArgumentException("Contact set is empty");
		}
		if (! doContactsAllExist(contacts)){
			throw new IllegalArgumentException("The set of contacts contains contacts that are not recognised");
		}
		if (contacts == null || date == null || text == null){
			throw new NullPointerException("Arguments cannot be null");
		}
		else{
			PastMeetingImpl newPastMeeting = new PastMeetingImpl(contacts, date, uniqueMeetingIdGenerator);
			uniqueMeetingIdGenerator++;
			newPastMeeting.addNotes(text);
			this.meetingSet.add(newPastMeeting);
			flush();
		}
	}

	/**
	 * Add notes to a meeting.
	 *
	 * This method is used when a future meeting takes place, and is
	 * then converted to a past meeting (with notes).
	 *
	 * It can be also used to add notes to a past meeting at a later date.
	 *
	 * @param id the ID of the meeting
	 * @param text messages to be added about the meeting.
	 * @throws IllegalArgumentException if the meeting does not exist
	 * @throws IllegalStateException if the meeting is set for a date in the future
	 * @throws NullPointerException if the notes are null
	 */
	public void addMeetingNotes(int id, String text){
		if (text == null){
			throw new NullPointerException("Notes must not be null");
		}
		for (MeetingImpl meeting : meetingSet){
			if (meeting.getId() == id){
				if (!isMeetingInPast(meeting)){
					throw new IllegalArgumentException("Meeting must not be in the future");
				}
				meeting.addNotes(text);
				updateMeetingList(); //converts future meetings to past meetings if they are now in the past
				flush();
				return;
			}
		}
		throw new IllegalArgumentException("Meeting not found");
	}

   	/**
	 * Create a new contact with the specified name and notes.
	 *
	 * @param name the name of the contact.
	 * @param notes notes to be added about the contact.
	 * @throws NullPointerException if the name or the notes are null
	 */
	public void addNewContact(String name, String notes){
		if (name == null || notes == null){
			throw new NullPointerException("Cannot have null name or null notes");
		}
		else{
			this.contactSet.add(new ContactImpl(name, notes, uniqueContactIdGenerator));
			uniqueContactIdGenerator++;
			flush();
		}

	}

	/**
	 * Returns a list containing the contacts that correspond to the IDs.
	 *
	 * @param ids an arbitrary number of contact IDs
	 * @return a list containing the contacts that correspond to the IDs.
	 * @throws IllegalArgumentException if any of the IDs does not correspond to a real contact
	 */
	public Set<Contact> getContacts(int... ids){
		Set<Contact> contactsWithInputtedIds = new HashSet<Contact>();
		for (int idNum: ids){
			boolean contactFound = false;  //for every Id, make sure there is a contact with the Id
			for (Contact contact : contactSet){
				if (contact.getId() == idNum){
					contactsWithInputtedIds.add(contact);
					contactFound = true;
				}
			}
			if (!contactFound){
				throw new IllegalArgumentException("Id " + idNum + " does not correspond to any real Contact");
			}
		}
		return contactsWithInputtedIds;
	}

	/**
	 * Returns a list with the contacts whose name contains that string.
	 *
	 * @param name the string to search for
	 * @return a list with the contacts whose name contains that string.
	 * @throws NullPointerException if the parameter is null
	 */
	public Set<Contact> getContacts(String name){
		if (name == null){
			throw new NullPointerException("name must not be null");
		}
		Set<Contact> contactsWithStringInName = new HashSet<Contact>();
		for (Contact contact : this.contactSet){
			if (contact.getName().indexOf(name) != -1){    //this means 'name' is in the name of the contact.
				contactsWithStringInName.add(contact);    //indexOf method returns -1 when a String does not contain the String in the paramater
			}
		}
		return contactsWithStringInName;
	}

	/**
	 * Save all data to disk.
	 *
	 * This method must be executed when the program is
	 * closed and when/if the user requests it.
	 */
	 //This method is automatically called whenever new information is added to contactSet or MeetingSet
	 //This has the same effect as calling this method when the program is closed as the data in the contacts file is always up-to-date
	public void flush(){
		updateMeetingList(); //Makes sure meetings in meetingSet have the correct class.
		File dataFile = new File("contacts.txt");
		if (dataFile.exists()){
			try{
				dataFile.delete(); //delete the file so new file can be written from scratch.
			}
			catch (Exception e){
				System.out.println("File cannot be deleted");
				e.printStackTrace();
			}
		}
		ObjectOutputStream out = null;
		try{
			out = new ObjectOutputStream(new FileOutputStream(dataFile));
			out.writeObject(contactSet);
			out.writeObject(meetingSet);
			out.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
		finally{
			try{
				if (out != null){
					out.close();
				}
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
	}

	/**
	*
	* Checks the Meetings stored in the Contact Manager. If a meeting has a past date but is stored as a FutureMeeting,
	* it is converted to a PastMeeting. If a meeting has a future date but is stored as PastMeeting, it is converted to a FutureMeeting.
	*
	*/
	private void updateMeetingList(){
		for (MeetingImpl meeting: meetingSet){
			if ((isMeetingInPast(meeting)) && (meeting.getClass() == FutureMeetingImpl.class)){
				PastMeetingImpl pastMeetingConverted = new PastMeetingImpl(meeting.getContacts(), meeting.getDate(), meeting.getId());
				pastMeetingConverted.addNotes(meeting.getNotes());
				meetingSet.remove(meeting);
				meetingSet.add(pastMeetingConverted);
			}
//the else clause below is necessary because the addNewPastMeeting allows a past meeting to be added with a future date based on the interface
			else if ((!isMeetingInPast(meeting)) && (meeting.getClass() == PastMeetingImpl.class)){
				FutureMeetingImpl futureMeetingConverted = new FutureMeetingImpl(meeting.getContacts(), meeting.getDate(), meeting.getId());
				meetingSet.remove(meeting);
				meetingSet.add(futureMeetingConverted);
			}
		}
	}
}