import java.util.Set;
import java.util.Calendar;

public class PastMeetingImpl extends MeetingImpl implements PastMeeting{
	public PastMeetingImpl(Set<Contact> contacts, Calendar date, int id){
		super(contacts, date, id);
	}
	public String getNotes(){
		return "";
	}

}