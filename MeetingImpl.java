import java.util.Calendar;
import java.util.Set;
import java.util.GregorianCalendar;
import java.util.HashSet;

public class MeetingImpl implements Meeting{
	private Set<Contact> contactSet = new HashSet<Contact>();
	private Calendar meetingDate = new GregorianCalendar();
	private int meetingId;
	private String notes;

	public MeetingImpl(Set<Contact> contacts, Calendar date, int id){
		this.meetingId = id;
		this.meetingDate = date;
		this.contactSet = contacts;
	}
	public int getId(){
		return meetingId;
	}
	public Calendar getDate(){
		return meetingDate;
	}
	public Set<Contact> getContacts(){
		return contactSet;
	}

	@Override
	public boolean equals(Object o){
		if (o == null){
			return false;
		}
		if (this.getClass() != o.getClass()){
			return false;
		}
		MeetingImpl meet = (MeetingImpl) o;
		if (this.getId() == meet.getId() && this.getDate().equals(meet.getDate()) && this.getContacts().equals(meet.getContacts())){
			return true;
		}
		else{
			return false;
		}
	}

	public void addNotes(String notes){
		this.notes = notes;
	}
}