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
		String nullString = null;
		contactManagerTester1.getContacts(nullString);
	}

	@Test
	public void testGetContactsNormal(){
		contactManagerTester1.addNewContact("John", "John notes");
		contactManagerTester1.addNewContact("Steven", "Steven notes");
		contactManagerTester1.addNewContact("Stewart", "Stewart notes");
		contactManagerTester1.addNewContact("Roger Steward", "Roger notes"); //contains 'ste' in surname so should be in expected result
		contactManagerTester1.addNewContact("Peter Salazar", "More notes");
		Set<Contact> expectedContactSet = new HashSet<Contact>();
		expectedContactSet.add(new ContactImpl("Steven", "Steven notes", 2));
		expectedContactSet.add(new ContactImpl("Stewart", "Stewart notes", 3));
		expectedContactSet.add(new ContactImpl("Roger Steward", "Roger notes", 4));
		Set<Contact> expected = expectedContactSet;
		Set<Contact> output = contactManagerTester1.getContacts(new String("Ste"));
		assertEquals(expected,output);
	}
}