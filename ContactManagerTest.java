import org.junit.*;
import static org.junit.Assert.*;
import java.util.Set;
import java.util.TreeSet;
import java.util.HashSet;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class ContactManagerTest{
	private ContactManagerImpl contactManagerTester1;
	private ContactManager contactManagerTester2;
	private Set<Contact> contacts = new HashSet<Contact>();
	private Set<Meeting> meetings = new TreeSet<Meeting>();
	Calendar randomDate = new GregorianCalendar(2014,12,10);

	@Before
	public void buildUp(){
		contactManagerTester1 = new ContactManagerImpl();
		contactManagerTester2 = new ContactManagerImpl();
	}

	@Test
	public void testAddNewContact(){
		contactManagerTester1.addNewContact("Simon", "These are tester notes");
		boolean expected = true;
		boolean output = contactManagerTester1.containsContact(new ContactImpl("Simon", "These are tester notes", 1));
		assertEquals(expected,output);
	}

	@Test (expected = NullPointerException.class)
	public void testAddNewContactWithNullFirstParaMeter(){
			contactManagerTester1.addNewContact(null, "These are tester notes for addNewContactMethod");
	}

	@Test (expected = NullPointerException.class)
	public void testAddNewContactWithNullSecondParaMeter(){
		contactManagerTester1.addNewContact("Simon", null);
	}

	@Test (expected = NullPointerException.class)
	public void testGetContactsNullParameter(){
		contactManagerTester1.getContacts(null);
	}
}