import java.util.Set;
import java.util.Calendar;
import java.io.*;
/**
* A meeting to be held in the future
*/
public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting, Serializable{
	public FutureMeetingImpl(Set<Contact> contacts, Calendar date, int id){
		super(contacts, date, id);
	}
}