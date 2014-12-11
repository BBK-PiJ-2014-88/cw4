public class ContactImpl implements Contact{
	private static int uniqueIdGenerator = 1;
	private int id;
	private String name;
	private String notes;

	public ContactImpl(){
		id = uniqueIdGenerator;
		uniqueIdGenerator++;
	}
	public int getId(){
		return id;
	}

	public String getName(){
		return "";
	}

	public String getNotes(){
		return "";
	}

	public void addNotes(String note){
	}
}