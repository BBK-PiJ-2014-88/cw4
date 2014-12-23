import org.junit.*;
import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.HashSet;


public class PastMeetingTest{
	private PastMeeting pastMeetingTester1;
	private Set<Contact> contactTesterSet = new HashSet<Contact>();

	@Before
	public void buildUp(){
		Calendar date = new GregorianCalendar(2014,12,05,11,30,00);
		contactTesterSet.add(new ContactImpl("John",1));
		contactTesterSet.add(new ContactImpl("Freddy",2));
		contactTesterSet.add(new ContactImpl("Xavier",3));
		pastMeetingTester1 = new PastMeetingImpl(contactTesterSet, date, 1);
	}

	@Test
	public void testGetId(){
		int expected = 1;
		int output = pastMeetingTester1.getId();
		assertEquals(expected,output);
	}

	@Test
	public void testGetDate(){
		Calendar expected = new GregorianCalendar(2014,12,05,11,30,00);
		Calendar output = pastMeetingTester1.getDate();
		assertEquals(expected,output);
	}

	@Test
	public void testGetContacts(){
		Set<Contact> expected = contactTesterSet;
		Set<Contact> output = pastMeetingTester1.getContacts();
		assertEquals(expected,output);
	}

	@Test
	public void testGetNotes(){
		String expected = "";
		String output = pastMeetingTester1.getNotes();
		assertEquals(expected,output);
	}
	//need to add another test in future after notes have been added. probably will need an extra constructor

}