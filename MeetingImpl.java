import java.util.Calendar;
import java.util.Set;

public class MeetingImpl implements Meeting{
	private static int uniqueIdGenerator = 1; //Increases by 1 every time a new meeting is made and assigned as the meeting unique ID.
	private Set<Contact> contactList;
	private Calendar date;
	private int meetingId;

	public MeetingImpl(Set<Contact> contacts, Calendar date){
		contactList = contacts;
		this.date = date;
		this.meetingId = uniqueIdGenerator;
		uniqueIdGenerator++;
	}
	public int getId(){
		return meetingId;
	}
	public Calendar getDate(){
		return null;
	}
	public Set<Contact> getContacts(){
		return null;
	}
}