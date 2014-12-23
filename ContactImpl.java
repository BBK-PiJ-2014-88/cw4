public class ContactImpl implements Contact{
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

	@Override
	public boolean equals(Object o){
		ContactImpl cont = (ContactImpl) o;
		if (this.getId() == cont.getId() && this.getName().equals(cont.getName()) && this.getNotes().equals(cont.getNotes())){
			return true;
		}
		else{
			return false;
		}
	}
}