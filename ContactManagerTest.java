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

	@Test
	public void testGetContactsNoneContainString(){
		contactManagerTester1.addNewContact("John", "John notes");
		contactManagerTester1.addNewContact("Steven", "Steven notes");
		contactManagerTester1.addNewContact("Stewart", "Stewart notes");
		contactManagerTester1.addNewContact("Roger Steward", "Roger notes"); //contains 'ste' in surname so should be in expected result
		contactManagerTester1.addNewContact("Peter Salazar", "More notes");
		Set<Contact> expectedContactSet = new HashSet<Contact>();
		Set<Contact> expected = expectedContactSet;
		Set<Contact> output = contactManagerTester1.getContacts(new String("zzz"));
		assertEquals(expected,output);
	}

	@Test
	public void testGetContactsWithID(){
		contactManagerTester1.addNewContact("John", "John notes");
		contactManagerTester1.addNewContact("Steven", "Steven notes");
		contactManagerTester1.addNewContact("Stewart", "Stewart notes");  //id = 3
		contactManagerTester1.addNewContact("Roger Steward", "Roger notes"); //id = 4
		contactManagerTester1.addNewContact("Peter Salazar", "More notes");
		Set<Contact> expectedContactSet = new HashSet<Contact>();
		expectedContactSet.add(new ContactImpl("Stewart", "Stewart notes", 3));
		expectedContactSet.add(new ContactImpl("Roger Steward", "Roger notes", 4));
		Set<Contact> expected = expectedContactSet;
		Set<Contact> output = contactManagerTester1.getContacts(3,4);
		assertEquals(expected,output);
	}
	@Test (expected = IllegalArgumentException.class)
	public void testGetContactsWithIDIllegalArgumentException(){
		contactManagerTester1.addNewContact("John", "John notes");
		contactManagerTester1.addNewContact("Steven", "Steven notes");
		contactManagerTester1.addNewContact("Stewart", "Stewart notes");  //id = 3
		contactManagerTester1.addNewContact("Roger Steward", "Roger notes"); //id = 4
		contactManagerTester1.addNewContact("Peter Salazar", "More notes");
		Set<Contact> expectedContactSet = new HashSet<Contact>();
		expectedContactSet.add(new ContactImpl("Stewart", "Stewart notes", 3));
		expectedContactSet.add(new ContactImpl("Roger Steward", "Roger notes", 4));
		contactManagerTester1.getContacts(3,4,7);  //should throw Exception as there is no contact with ID of 7
	}

	@Test (expected = IllegalArgumentException.class)
	public void testGetContactWithIDnoParameters(){
		contactManagerTester1.addNewContact("John", "John notes");
		contactManagerTester1.addNewContact("Steven", "Steven notes");
		contactManagerTester1.addNewContact("Stewart", "Stewart notes");
		contactManagerTester1.getContacts(0);
	}

	// addFutureMeeting tests here

	@Test (expected = IllegalArgumentException.class) //adding Contact Set containing unknown contact to Contact Manager
	public void testAddFutureMeetingWithUnknownContact(){
		contactManagerTester1.addNewContact("John", "John notes");   //Added 3 contacts. Will try to add a meeting with contact not added to
		contactManagerTester1.addNewContact("Steven", "Steven notes"); //the Contact Manager.
		contactManagerTester1.addNewContact("Stewart", "Stewart notes");
		Set<Contact> contactSetWithUnknown = new HashSet<Contact>();
		contactSetWithUnknown.add(new ContactImpl("John", "John notes", 1));
		contactSetWithUnknown.add(new ContactImpl("Simon", "Simon is not in contact Manager's known contacts", 2));
		Calendar futureDate = new GregorianCalendar(2016,12,12);
		contactManagerTester1.addFutureMeeting(contactSetWithUnknown, futureDate);
	}

		@Test (expected = IllegalArgumentException.class) //Adding time in the past as date for future meeting
		public void testAddFutureMeetingWithPastDate(){
			contactManagerTester1.addNewContact("John", "John notes");
			contactManagerTester1.addNewContact("Steven", "Steven notes");
			Set<Contact> contactSetWithUnknown = new HashSet<Contact>();
			contactSetWithUnknown.add(new ContactImpl("John", "John notes", 1));
			contactSetWithUnknown.add(new ContactImpl("Steven", "Steven notes", 2));
			Calendar pastDate = new GregorianCalendar(2008,12,12);
			contactManagerTester1.addFutureMeeting(contactSetWithUnknown, pastDate);
	}

		@Test  //Adding future meeting with correct Parameters. Expect meeting id
		public void testAddFutureMeetingWithCorrectParameter(){
			contactManagerTester1.addNewContact("John", "John notes");
			contactManagerTester1.addNewContact("Steven", "Steven notes");
			Set<Contact> contactSetWithUnknown = new HashSet<Contact>();
			contactSetWithUnknown.add(new ContactImpl("John", "John notes", 1));
			contactSetWithUnknown.add(new ContactImpl("Steven", "Steven notes", 2));
			Calendar futureDate = new GregorianCalendar(2015,12,12);
			int expected = 1;
			int output = contactManagerTester1.addFutureMeeting(contactSetWithUnknown, futureDate);
			assertEquals(expected,output);
	}

		@Test  //Adding multiple future meeting with correct Parameters. Expect meeting id
		public void testAddMultipleFutureMeetingWithCorrectParameter(){
			contactManagerTester1.addNewContact("John", "John notes");
			contactManagerTester1.addNewContact("Steven", "Steven notes");
			Set<Contact> contactSetWithUnknown = new HashSet<Contact>();
			contactSetWithUnknown.add(new ContactImpl("John", "John notes", 1));
			contactSetWithUnknown.add(new ContactImpl("Steven", "Steven notes", 2));
			Calendar futureDate = new GregorianCalendar(2015,12,12);
			contactManagerTester1.addFutureMeeting(contactSetWithUnknown, futureDate); //meeting 1
			contactManagerTester1.addFutureMeeting(contactSetWithUnknown, futureDate); //meeting 2
			int expected = 3;
			int output = contactManagerTester1.addFutureMeeting(contactSetWithUnknown, futureDate);
			assertEquals(expected,output);
	}


}