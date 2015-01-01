import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.HashSet;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.ArrayList;
import java.io.*;

public class ContactManagerImpl implements ContactManager, Serializable{
	private Set<Contact> contactSet = new HashSet<Contact>();
	private Set<MeetingImpl> meetingSet = new TreeSet<MeetingImpl>();
	private int uniqueContactIdGenerator = 1;
	private int uniqueMeetingIdGenerator = 1;

	public ContactManagerImpl(){
		File dataFile = new File("contacts.txt");
		Set<MeetingImpl> meetingSetTemp = new HashSet<MeetingImpl>();
		if (dataFile.exists()){
			try{
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(dataFile));
				contactSet = (HashSet) in.readObject();
				System.out.println("Done contact read");
				meetingSet = (TreeSet) in.readObject();
				System.out.println("Done meeting read");
				in.close();
				System.out.println("closed");
			}
			catch (IOException e){
				e.printStackTrace();
			}
			catch (ClassNotFoundException e){
				e.printStackTrace();
			}
			meetingSet.addAll(meetingSetTemp);
		}
	}

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
			return newFutureMeeting.getId();
		}
	}

	/**
	* Checks that all the contacts in the Contact Set are known
	*
	* @param a set of contacts to check
	* @return a boolean. True if all the contacts exist. False if they don't
	*/
	public boolean doContactsAllExist(Set<Contact> contacts){
		for (Contact cont: contacts){
			if (!contactSet.contains(cont)){
				return false;
			}
		}
		return true;
	}

	public boolean containsContact(Contact con){
		return this.contactSet.contains(con);
	}

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

	public boolean isMeetingInPast(Meeting meeting){
		Calendar currentTime = Calendar.getInstance();
		if (meeting.getDate().before(currentTime)){
			return true;
		}
		else{
			return false;
		}
	}

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
	public Meeting getMeeting(int id){
		for (Meeting meeting: meetingSet){
			if (meeting.getId() == id){
					return (Meeting) meeting;
				}
		}
		return null;
	}

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
	public boolean isSameDate(Calendar date1, Calendar date2){
		if (date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR) && date1.get(Calendar.MONTH) == date2.get(Calendar.MONTH)
		&& date1.get(Calendar.DAY_OF_MONTH) == date2.get(Calendar.DAY_OF_MONTH)){
			return true;
		}
		return false;
	}

	public List<PastMeeting> getPastMeetingList(Contact contact){
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
		}
	}
	public void addMeetingNotes(int id, String text){
		if (text == null){
			throw new NullPointerException("Notes must not be null");
		}
		for (MeetingImpl meeting : meetingSet){
			if (meeting.getId() == id){
				if (new GregorianCalendar().getInstance().before(meeting.getDate())){
					throw new IllegalArgumentException("Meeting must not be in the future");
				}
				meeting.addNotes(text);
				return;
			}
		}
		throw new IllegalArgumentException("Meeting not found");
	}
	public void addNewContact(String name, String notes){
		if (name == null || notes == null){
			throw new NullPointerException("Cannot have null name or null notes");
		}
		else{
			this.contactSet.add(new ContactImpl(name, notes, uniqueContactIdGenerator));
			uniqueContactIdGenerator++;
		}

	}
	public Set<Contact> getContacts(int... ids){
		Set<Contact> contactsWithInputtedIds = new HashSet<Contact>();
		for (int idNum: ids){
			boolean contactFound = false;
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
	public void flush(){
		File dataFile = new File("contacts.txt");
		if (dataFile.exists()){
			try{
				dataFile.delete();
				System.out.println("File deleted");
			}
			catch (Exception e){
				System.out.println("File cannot be deleted");
				e.printStackTrace();
			}
		}
		try{
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(dataFile));
			out.writeObject(contactSet);
			System.out.println("Done contact write");
			out.writeObject(meetingSet);
			System.out.println("Done meeting write");
			out.close();
			System.out.println("out closed successfully");
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}

	/**
	*
	* Checks the Meetings stored in the Contact Manager. If a meeting has a past date but is stored as a FutureMeeting,
	* it is converted to a PastMeeting. If a meeting has a future date but is stored as PastMeeting, it is converted to a FutureMeeting.
	*
	*/
	public void updateMeetingList(){
		for (Meeting meeting: meetingSet){
			if ((isMeetingInPast(meeting)) && (meeting.getClass() == FutureMeetingImpl.class)){
				PastMeetingImpl pastMeetingConverted = new PastMeetingImpl(meeting.getContacts(), meeting.getDate(), meeting.getId());
				meetingSet.remove(meeting);
				meetingSet.add(pastMeetingConverted);
			}
			else if ((!isMeetingInPast(meeting)) && (meeting.getClass() == PastMeetingImpl.class)){
				FutureMeetingImpl futureMeetingConverted = new FutureMeetingImpl(meeting.getContacts(), meeting.getDate(), meeting.getId());
				meetingSet.remove(meeting);
				meetingSet.add(futureMeetingConverted);
			}
		}
	}
}