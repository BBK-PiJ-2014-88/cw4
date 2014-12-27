import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.HashSet;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.ArrayList;

public class ContactManagerImpl implements ContactManager{
	private Set<ContactImpl> contactSet = new HashSet<ContactImpl>();
	private Set<MeetingImpl> meetingSet = new TreeSet<MeetingImpl>(new MeetingComparator());

	private int uniqueContactIdGenerator = 1;
	private int uniqueMeetingIdGenerator = 1;

	public int addFutureMeeting(Set<Contact> contacts, Calendar date){
		if (! doContactsAllExist(contacts)){
			throw new IllegalArgumentException("Contact not known");
		}
		if (new GregorianCalendar().getInstance().after(date)){
			throw new IllegalArgumentException("Date must be in future");
		}
		else{
			MeetingImpl newFutureMeeting = new FutureMeetingImpl(contacts, date, uniqueMeetingIdGenerator);
			uniqueMeetingIdGenerator++;
			meetingSet.add(newFutureMeeting);
			return newFutureMeeting.getId();
		}
	}

	public boolean doContactsAllExist(Set<Contact> contacts){  //checks if contacts are unknown/non-existent
		for (Contact cont: contacts){
			if (!containsContact((ContactImpl)cont)){
				return false;
			}
		}
		return true;
	}


	public PastMeeting getPastMeeting(int id){
		for (MeetingImpl meeting: meetingSet){
			if (meeting.getId() == id){
				if (!isMeetingInPast(meeting)){
					throw new IllegalArgumentException("Requested Meeting must have already taken place");
				}
				else{
					return (PastMeetingImpl) meeting;
				}
			}
		}
		return null;
	}

	public boolean isMeetingInPast(MeetingImpl meeting){
		Calendar currentTime = Calendar.getInstance();
		if (meeting.getDate().before(currentTime)){
			return true;
		}
		else{
			return false;
		}
	}

	public FutureMeeting getFutureMeeting(int id){
		for (MeetingImpl meeting: meetingSet){
			if (meeting.getId() == id){
				if (isMeetingInPast(meeting)){
					throw new IllegalArgumentException("Requested Meeting must be in future");
				}
				else{
					return (FutureMeetingImpl) meeting;
				}
			}
		}
		return null;

	}
	public Meeting getMeeting(int id){
		for (MeetingImpl meeting: meetingSet){
			if (meeting.getId() == id){
					return meeting;
				}
		}
		return null;
	}
	public List<Meeting> getFutureMeetingList(Contact contact){
		List<Meeting> meetingsWithContact = new ArrayList<Meeting>();
		boolean contactFound = false;
		for (MeetingImpl meeting : meetingSet){
			for (Contact meetingContact : meeting.getContacts()){
				if (meetingContact.equals(contact)){
					contactFound = true;
					if(!isMeetingInPast(meeting)){
						meetingsWithContact.add(meeting);
					}
				}
			}
		}
		if (contactFound){
			return meetingsWithContact;
		}
		throw new IllegalArgumentException("Contact not found");
	}
	public List<Meeting> getFutureMeetingList(Calendar date){
		List<Meeting> meetingsOnDate = new ArrayList<Meeting>();
		for (MeetingImpl meeting : meetingSet){
			if (isSameDate(meeting.getDate(), date)){
				meetingsOnDate.add(meeting);
			}
		}
		return meetingsOnDate;
	}

	public boolean isSameDate(Calendar date1, Calendar date2){
		if (date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR) && date1.get(Calendar.MONTH) == date2.get(Calendar.MONTH)
		&& date1.get(Calendar.DAY_OF_MONTH) == date2.get(Calendar.DAY_OF_MONTH)){
			return true;
		}
		return false;
	}

	public List<PastMeeting> getPastMeetingList(Contact contact){
		return null;
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
			MeetingImpl newPastMeeting = new PastMeetingImpl(contacts, date, uniqueMeetingIdGenerator);
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
			for (ContactImpl contact : contactSet){
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
		for (ContactImpl contact : this.contactSet){
			if (contact.getName().indexOf(name) != -1){
				contactsWithStringInName.add(contact);
			}
		}
		return contactsWithStringInName;
	}
	public void flush(){
	}

	public boolean containsContact(ContactImpl con){  //used this method for junit testing
		return this.contactSet.contains(con);
	}

	public void updateMeetingList(){
		for (MeetingImpl meeting: meetingSet){
			if ((isMeetingInPast(meeting)) && (meeting.getClass() == FutureMeetingImpl.class)){
				PastMeetingImpl pastMeetingConverted = new PastMeetingImpl(meeting.getContacts(), meeting.getDate(), meeting.getId());
				meetingSet.remove(meeting);
				meetingSet.add(pastMeetingConverted);
			}
		}
	}

	private class MeetingComparator implements Comparator<MeetingImpl>{
		@Override
		public int compare(MeetingImpl meeting1, MeetingImpl meeting2){
			if (meeting1.getDate().before(meeting2.getDate())){
				return -1;
			}
			else if (meeting1.getDate().after(meeting2.getDate())){
				return 1;
			}
			else{
				return 0;
			}
		}
	}
}