import java.io.Serializable;
/**
* A contact is a person we are making business with or may do in the future.
*
* Contacts have an ID (unique), a name (probably unique, but maybe
* not), and notes that the user may want to save about them.
*/
public class ContactImpl implements Contact,Serializable{
	private int id;
	private String name;
	private String notes;

	public ContactImpl(String name, int id){
		this.id = id;
		this.name = name;
	}
	public ContactImpl(String name, String notes, int id){
		this.name = name;
		this.notes = notes;
		this.id = id;
	}
	/**
	 * Returns the ID of the contact.
	 *
	 * @return the ID of the contact.
	 */
	public int getId(){
		return id;
	}
	/**
	 * Returns the name of the contact.
	 *
	 * @return the name of the contact.
	 */
	public String getName(){
		return name;
	}
	/**
	 * Returns our notes about the contact, if any.
	 *
	 * If we have not written anything about the contact, the empty
	 * string is returned.
	 *
	 * @return a string with notes about the contact, maybe empty.
	 */
	public String getNotes(){
		return notes;
	}
	/**
	 * Add notes about the contact.
	 *
	 * @param note the notes to be added
	 */
	public void addNotes(String note){
		notes = note;
	}

	@Override
	public boolean equals(Object o){
		if (o == null){
			return false;
		}
		if (this.getClass() != o.getClass()){
			return false;
		}
		ContactImpl cont = (ContactImpl) o;
		if (this.getId() == cont.getId() && this.getName().equals(cont.getName()) && this.getNotes().equals(cont.getNotes())){
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public int hashCode(){
		String hashCodeString = this.getId() + this.getName() + this.getNotes();
		int newHashCode = hashCodeString.hashCode();
		return newHashCode;
	}
}