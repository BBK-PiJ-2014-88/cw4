import java.util.Calendar;
import java.util.Set;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.io.Serializable;

public class MeetingImpl implements Meeting, Serializable, Comparable{
	private Set<Contact> contactSet = new HashSet<Contact>();
	private Calendar meetingDate = new GregorianCalendar();
	private int meetingId;
	private String notes = "";

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

	public void addNotes(String notes){
		this.notes = this.notes + notes + "\n";
	}
	public String getNotes(){
		return notes;
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

	@Override
	public int compareTo(Object other){
		MeetingImpl meeting2 = (MeetingImpl) other;
		if (this.getDate().before(meeting2.getDate())){
			return -1;
		}
		else if (this.getDate().after(meeting2.getDate())){
			return 1;
		}
		else{
			return 0;
		}
	}

}