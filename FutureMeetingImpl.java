import java.util.Set;
import java.util.Calendar;

public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting{
	public FutureMeetingImpl(Set<Contact> contacts, Calendar date){
		super(contacts, date);
	}
}