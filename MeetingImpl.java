import java.util.Calendar;
import java.util.Set;
import java.util.GregorianCalendar;

public class MeetingImpl implements Meeting{
	private static int uniqueIdGenerator = 1; //Increases by 1 every time a new meeting is made and assigned as the meeting unique ID.
	private Set<Contact> contactSet;
	private Calendar date = new GregorianCalendar();
	private int meetingId;

	public MeetingImpl(Set<Contact> contacts, Calendar date){
		this.date = date;
		this.contactSet = contacts;
		this.meetingId = uniqueIdGenerator;
		uniqueIdGenerator++;
	}
	public int getId(){
		return meetingId;
	}
	public Calendar getDate(){
		return date;
	}
	public Set<Contact> getContacts(){
		return null;
	}
}