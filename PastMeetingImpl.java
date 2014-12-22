import java.util.Set;
import java.util.Calendar;

public class PastMeetingImpl extends MeetingImpl implements PastMeeting{
	public PastMeetingImpl(Set<Contact> contacts, Calendar date){
		super(contacts, date);
	}
	public String getNotes(){
		return "";
	}

}