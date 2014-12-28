import java.io.Serializable;

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