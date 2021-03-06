import java.util.Set;
import java.util.Calendar;
import java.io.*;
/**
* A meeting that was held in the past.
*
* It includes your notes about what happened and what was agreed.
*/
public class PastMeetingImpl extends MeetingImpl implements PastMeeting, Serializable{
	public PastMeetingImpl(Set<Contact> contacts, Calendar date, int id){
		super(contacts, date, id);
	}
	//inherits getNotes from MeetingImpl


	@Override
	public boolean equals(Object o){
		if (o == null){
			return false;
		}
		if (this.getClass() != o.getClass()){
			return false;
		}
		PastMeetingImpl meet = (PastMeetingImpl) o;
		if (this.getId() == meet.getId() && this.getDate().equals(meet.getDate()) && this.getContacts().equals(meet.getContacts())
		&& this.getNotes().equals(meet.getNotes())){
			return true;
		}
		else{
			return false;
		}
	}
}