import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.HashSet;
import java.util.Comparator;
import java.util.GregorianCalendar;

public class ContactManagerImpl implements ContactManager{
	private Set<ContactImpl> contactSet = new HashSet<ContactImpl>();
	private Set<MeetingImpl> meetingSet = new TreeSet<MeetingImpl>(new MeetingComparator());

	private int uniqueContactIdGenerator = 1;
	private int uniqueMeetingIdGenerator = 1;

	public int addFutureMeeting(Set<Contact> contacts, Calendar date){
		if (! doesContactExist(contacts)){
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

	public boolean doesContactExist(Set<Contact> contacts){  //checks if contacts are unknown/non-existent
		for (Contact cont: contacts){
			if (!containsContact((ContactImpl)cont)){
				return false;
			}
		}
		return true;
	}


	public PastMeeting getPastMeeting(int id){
		return null;
	}
	public FutureMeeting getFutureMeeting(int id){
		return null;
	}
	public Meeting getMeeting(int id){
		return null;
	}
	public List<Meeting> getFutureMeetingList(Contact contact){
		return null;
	}
	public List<Meeting> getFutureMeetingList(Calendar date){
		return null;
	}
	public List<PastMeeting> getPastMeetingList(Contact contact){
		return null;
	}
	public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text){

	}
	public void addMeetingNotes(int id, String text){
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