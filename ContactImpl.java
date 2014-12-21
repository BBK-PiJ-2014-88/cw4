public class ContactImpl implements Contact{
	private static int uniqueIdGenerator = 1; //Increases by 1 every time a new contact is made and assigned as the contact unique ID.
	private int id;
	private String name;
	private String notes;

	public ContactImpl(String name){
		id = uniqueIdGenerator;
		uniqueIdGenerator++;
		this.name = name;
	}
	public int getId(){
		return id;
	}

	public String getName(){
		return name;
	}

	public String getNotes(){
		return notes;
	}

	public void addNotes(String note){
		notes = note;
	}
}