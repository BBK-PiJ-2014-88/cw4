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
	Contact testerContact = new ContactImpl("Simon");   //for testing addContact method

	@Before
	public void buildUp(){
		contactManagerTester1 = new ContactManagerImpl();
		contactManagerTester2 = new ContactManagerImpl();
		contacts.add(new ContactImpl("Smith"));
		contacts.add(new ContactImpl("John"));
		contacts.add(new ContactImpl("Steven"));

	}

	/*@Test
	public void testAddFutureMeeting(){
		int expected = 1;
		int output = contactManagerTester1.addFutureMeeting(contacts,randomDate);
		assertEquals(expected,output);
	}*/

	@Test
	public void testAddNewContact(){
		testerContact.addNotes("These are tester notes for addNewContactMethod");
		contactManagerTester1.addNewContact("Simon", "These are tester notes for addNewContactMethod");
		boolean expected = true;
		boolean output = contactManagerTester1.containsContact(testerContact);
		assertEquals(expected,output);
	}

	@Test (expected = NullPointerException.class)
	public void testAddNewContactWithNullFirstParaMeter(){
		testerContact.addNotes("These are tester notes for addNewContactMethod");
		contactManagerTester1.addNewContact(null, "These are tester notes for addNewContactMethod");
	}

	@Test (expected = NullPointerException.class)
	public void testAddNewContactWithNullSecondParaMeter(){
		testerContact.addNotes("These are tester notes for addNewContactMethod");
		contactManagerTester1.addNewContact("Simon", null);
	}
}