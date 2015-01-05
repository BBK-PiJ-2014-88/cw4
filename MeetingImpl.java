import java.util.Calendar;
import java.util.Set;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.io.Serializable;
/**
* A class to represent meetings
*
* Meetings have unique IDs, scheduled date and a list of participating contacts
*/
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
	/**
	 * Returns the id of the meeting.
	 *
	 * @return the id of the meeting.
	 */
	public int getId(){
		return meetingId;
	}
	 /**
	 * Return the date of the meeting.
	 *
	 * @return the date of the meeting.
	 */
	public Calendar getDate(){
		return meetingDate;
	}
	 /**
	 * Return the details of people that attended the meeting.
	 *
	 * The list contains a minimum of one contact (if there were
	 * just two people: the user and the contact) and may contain an
	 * arbitraty number of them.
	 *
	 * @return the details of people that attended the meeting.
	 */
	public Set<Contact> getContacts(){
		return contactSet;
	}

	 /**
	 * Adds notes to a meeting
	 *
	 * @param the notes to add
	 */
	public void addNotes(String notes){
		this.notes = this.notes + notes + "\n";
	}
	 /**
	 * Gets the notes of a meeting
	 *
	 * @return the meeting notes
	 */
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
		if (other == null){
			throw new NullPointerException();
		}
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