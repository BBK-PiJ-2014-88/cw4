public class ContactImpl implements Contact{
	private static int uniqueIdGenerator = 1;
	private int id;
	private String name;
	private String notes;

	public ContactImpl(){
	}
	public int getId(){
		return 1;
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