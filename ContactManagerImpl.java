import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.HashSet;

public class ContactManagerImpl implements ContactManager{
	private Set<Contact> contactSet = new HashSet<Contact>();
	private Set<Meeting> meetingSet = new TreeSet<Meeting>();

	private int uniqueContactIdGenerator = 1;
	private int uniqueMeetingIdGenerator = 1;

	public int addFutureMeeting(Set<Contact> contacts, Calendar date){
		Meeting newFutureMeeting = new MeetingImpl(contacts, date);
		return newFutureMeeting.getId();
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
			this.contactSet.add(new ContactImpl(name, notes));
		}

	}
	public Set<Contact> getContacts(int... ids){
		return null;
	}
	public Set<Contact> getContacts(String name){
		return null;
	}
	public void flush(){
	}
	public boolean containsContact(Contact con){  //used this method for junit testing
		return this.contactSet.contains(con);
	}
}