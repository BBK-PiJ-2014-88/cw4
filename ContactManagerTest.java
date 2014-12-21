import org.junit.*;
import static org.junit.Assert.*;
import java.util.Set;
import java.util.TreeSet;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class ContactManagerTest{
	private ContactManager contactManagerTester1;
	private ContactManager contactManagerTester2;
	private Set<Contact> contacts = new TreeSet<Contact>();
	private Set<Meeting> meetings = new TreeSet<Meeting>();
	Calendar randomDate = new GregorianCalendar(2014,12,10);

	@Before
	public void buildUp(){
		contactManagerTester1 = new ContactManagerImpl();
		contactManagerTester2 = new ContactManagerImpl();
		contacts.add(new ContactImpl("Smith"));
		contacts.add(new ContactImpl("John"));
		contacts.add(new ContactImpl("Steven"));

	}

	@Test
	public void testAddFutureMeeting(){
		int expected = 1;
		int output = contactManagerTester1.addFutureMeeting(contacts,randomDate);
		assertEquals(expected,output);
	}
}