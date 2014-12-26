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
	private Set<Contact> contacts;
	private Set<Meeting> meetings;
	Calendar randomDate = new GregorianCalendar(2014,12,10);

	@Before
	public void buildUp(){
		contactManagerTester1 = new ContactManagerImpl();
		contactManagerTester2 = new ContactManagerImpl();
		contacts = new HashSet<Contact>();
		meetings = new TreeSet<Meeting>();
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

	// addFutureMeeting tests start here

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

	//addNewPastMeeting tests start here

	@Test (expected = IllegalArgumentException.class) //adding ContactSet containing unknown contact to Contact Manager
	public void testAddPastMeetingWithUnknownContact(){
		contactManagerTester1.addNewContact("John", "John notes");   //Added 3 contacts. Will try to add a meeting with contact not added to
		contactManagerTester1.addNewContact("Steven", "Steven notes"); //the Contact Manager.
		contactManagerTester1.addNewContact("Stewart", "Stewart notes");
		Set<Contact> contactSetWithUnknown = new HashSet<Contact>();
		contactSetWithUnknown.add(new ContactImpl("John", "John notes", 1));
		contactSetWithUnknown.add(new ContactImpl("Simon", "Simon is not in contact Manager's known contacts", 2));
		Calendar pastDate = new GregorianCalendar(2008,12,12);
		contactManagerTester1.addNewPastMeeting(contactSetWithUnknown, pastDate, "notes");
	}

	@Test (expected = IllegalArgumentException.class) //adding empty ContactSet as parameter
	public void testAddPastMeetingWithEmptyContactSet(){
		contactManagerTester1.addNewContact("John", "John notes");
		Set<Contact> contactSetEmpty = new HashSet<Contact>();
		Calendar pastDate = new GregorianCalendar(2008,12,12);
		contactManagerTester1.addNewPastMeeting(contactSetEmpty, pastDate, "notes");
	}
	@Test (expected = NullPointerException.class) //one of the arguments is null
	public void testAddPastMeetingWithNullContacts(){
		contactManagerTester1.addNewContact("John", "John notes");
		Set<Contact> contactSetNull = null;
		Calendar pastDate = new GregorianCalendar(2008,12,12);
		contactManagerTester1.addNewPastMeeting(contactSetNull, pastDate, "notes");
	}
	@Test (expected = NullPointerException.class) //one of the arguments is null
	public void testAddPastMeetingWithNullDate(){
		contactManagerTester1.addNewContact("John", "John notes");
		Set<Contact> contactSet = new HashSet<Contact>();
		contactSet.add(new ContactImpl("John", "John notes", 1));
		Calendar pastDate = null;
		contactManagerTester1.addNewPastMeeting(contactSet, pastDate, "notes");
	}
	@Test (expected = NullPointerException.class) //one of the arguments is null
	public void testAddPastMeetingWithNullNotes(){
		contactManagerTester1.addNewContact("John", "John notes");
		Set<Contact> contactSet = new HashSet<Contact>();
		contactSet.add(new ContactImpl("John", "John notes", 1));
		Calendar pastDate = new GregorianCalendar(2008,12,12);
		contactManagerTester1.addNewPastMeeting(contactSet, pastDate, null);
	}
	/*
	@Test  //adding PastMeeting Correctly
	public void testAddPastMeeting(){
		contactManagerTester1.addNewContact("John", "John notes");
		contactManagerTester1.addNewContact("Steven", "Steven notes");
		contactManagerTester1.addNewContact("Stewart", "Stewart notes");
		Set<Contact> contactSet = new HashSet<Contact>();
		contactSet.add(new ContactImpl("John", "John notes", 1));
		contactSet.add(new ContactImpl("Steven", "Steven notes", 2));
		contactSet.add(new ContactImpl("Stewart", "Stewart notes", 3));
		Calendar pastDate = new GregorianCalendar(2008,12,12);
		contactManagerTester1.addNewPastMeeting(contactSet, pastDate, "notes");
		//use getPastMeeting method to check its been added correctly
	}*/



	//getPastMeeting tests start here

	@Test
	public void testGetPastMeeting(){ //correct parameters
		contactManagerTester2.addNewContact("Daniel", "Daniel notes");
		contactManagerTester2.addNewContact("Smith", "Smith notes");
		contactManagerTester2.addNewContact("Chris", "Chris notes");
		contacts.add(new ContactImpl("Daniel", "Daniel notes",1));
		contacts.add(new ContactImpl("Smith", "Smith notes",2));
		contacts.add(new ContactImpl("Chris", "Chris notes",3));
		Calendar date1 = new GregorianCalendar(2011,12,10);
		Calendar date2 = new GregorianCalendar(2012,12,10);
		Calendar date3 = new GregorianCalendar(2014,12,10);
		contactManagerTester2.addNewPastMeeting(contacts, date1, "notes"); //meeting id 1
		contactManagerTester2.addNewPastMeeting(contacts, date2, "notes"); 	//meeting id 2
		contactManagerTester2.addNewPastMeeting(contacts, date3, "notes");	//meeting id 3
		PastMeetingImpl expectedMeeting = new PastMeetingImpl(contacts, date2, 2);
		expectedMeeting.addNotes("notes");
		PastMeeting expected = expectedMeeting;
		PastMeeting output = contactManagerTester2.getPastMeeting(2);
		assertEquals(expected,output);
	}

	@Test (expected = IllegalArgumentException.class)
	public void testGetPastMeetingFutureDate(){ //input ID for meeting hapenning in future
		contactManagerTester2.addNewContact("Daniel", "Daniel notes");
		contactManagerTester2.addNewContact("Smith", "Smith notes");
		contactManagerTester2.addNewContact("Chris", "Chris notes");
		contacts.add(new ContactImpl("Daniel", "Daniel notes",1));
		contacts.add(new ContactImpl("Smith", "Smith notes",2));
		contacts.add(new ContactImpl("Chris", "Chris notes",3));
		Calendar date1 = new GregorianCalendar(2011,12,10);
		Calendar date2 = new GregorianCalendar(2012,12,10);
		Calendar date3 = new GregorianCalendar(2015,12,10);
		contactManagerTester2.addNewPastMeeting(contacts, date1, "notes"); //meeting id 1
		contactManagerTester2.addNewPastMeeting(contacts, date2, "notes"); 	//meeting id 2
		contactManagerTester2.addNewPastMeeting(contacts, date3, "notes");	//has future date, should throw exception
		contactManagerTester2.getPastMeeting(3);
	}

	@Test
	public void testGetPastMeetingIDNonExistant(){ //No meeting with inputted ID
		contactManagerTester2.addNewContact("Daniel", "Daniel notes");
		contactManagerTester2.addNewContact("Smith", "Smith notes");
		contactManagerTester2.addNewContact("Chris", "Chris notes");
		contacts.add(new ContactImpl("Daniel", "Daniel notes",1));
		contacts.add(new ContactImpl("Smith", "Smith notes",2));
		contacts.add(new ContactImpl("Chris", "Chris notes",3));
		Calendar date1 = new GregorianCalendar(2011,12,10);
		Calendar date2 = new GregorianCalendar(2012,12,10);
		Calendar date3 = new GregorianCalendar(2014,12,10);
		contactManagerTester2.addNewPastMeeting(contacts, date1, "notes"); //meeting id 1
		contactManagerTester2.addNewPastMeeting(contacts, date2, "notes"); 	//meeting id 2
		contactManagerTester2.addNewPastMeeting(contacts, date3, "notes");	//meeting id 3
		PastMeeting expectedMeeting = null;
		PastMeeting expected = expectedMeeting;
		PastMeeting output = contactManagerTester2.getPastMeeting(4);
		assertEquals(expected,output);
	}

	//getFutureMeeting tests start here
	@Test
	public void testGetFutureMeeting(){ //correct parameters
		contactManagerTester2.addNewContact("Daniel", "Daniel notes");
		contactManagerTester2.addNewContact("Smith", "Smith notes");
		contactManagerTester2.addNewContact("Chris", "Chris notes");
		contacts.add(new ContactImpl("Daniel", "Daniel notes",1));
		contacts.add(new ContactImpl("Smith", "Smith notes",2));
		contacts.add(new ContactImpl("Chris", "Chris notes",3));
		Calendar date1 = new GregorianCalendar(2015,12,10);
		Calendar date2 = new GregorianCalendar(2016,12,10);
		Calendar date3 = new GregorianCalendar(2015,12,10);
		contactManagerTester2.addFutureMeeting(contacts, date1); //meeting id 1
		contactManagerTester2.addFutureMeeting(contacts, date2); 	//meeting id 2
		contactManagerTester2.addFutureMeeting(contacts, date3);	//meeting id 3
		FutureMeetingImpl expectedMeeting = new FutureMeetingImpl(contacts, date2, 2);
		FutureMeeting expected = expectedMeeting;
		FutureMeeting output = contactManagerTester2.getFutureMeeting(2);
		assertEquals(expected,output);
	}

	@Test (expected = IllegalArgumentException.class)
	public void testGetFutureMeetingPastDate(){ //input ID for meeting hapenning in past
		contactManagerTester2.addNewContact("Daniel", "Daniel notes");
		contactManagerTester2.addNewContact("Smith", "Smith notes");
		contactManagerTester2.addNewContact("Chris", "Chris notes");
		contacts.add(new ContactImpl("Daniel", "Daniel notes",1));
		contacts.add(new ContactImpl("Smith", "Smith notes",2));
		contacts.add(new ContactImpl("Chris", "Chris notes",3));
		Calendar date1 = new GregorianCalendar(2015,12,10);
		Calendar date2 = new GregorianCalendar(2016,12,10);
		Calendar date3 = new GregorianCalendar(2008,12,10);
		contactManagerTester2.addFutureMeeting(contacts, date1); //meeting id 1
		contactManagerTester2.addFutureMeeting(contacts, date2); 	//meeting id 2
		contactManagerTester2.addFutureMeeting(contacts, date3);	//has past date, should throw exception
		contactManagerTester2.getFutureMeeting(3);
	}

	@Test
	public void testGetFutureMeetingIDNonExistant(){ //No meeting with inputted ID
		contactManagerTester2.addNewContact("Daniel", "Daniel notes");
		contactManagerTester2.addNewContact("Smith", "Smith notes");
		contactManagerTester2.addNewContact("Chris", "Chris notes");
		contacts.add(new ContactImpl("Daniel", "Daniel notes",1));
		contacts.add(new ContactImpl("Smith", "Smith notes",2));
		contacts.add(new ContactImpl("Chris", "Chris notes",3));
		Calendar date1 = new GregorianCalendar(2015,12,10);
		Calendar date2 = new GregorianCalendar(2015,12,10);
		Calendar date3 = new GregorianCalendar(2015,12,10);
		contactManagerTester2.addFutureMeeting(contacts, date1); //meeting id 1
		contactManagerTester2.addFutureMeeting(contacts, date2); 	//meeting id 2
		contactManagerTester2.addFutureMeeting(contacts, date3);	//meeting id 3
		FutureMeeting expectedMeeting = null;
		FutureMeeting expected = expectedMeeting;
		FutureMeeting output = contactManagerTester2.getFutureMeeting(4);
		assertEquals(expected,output);
	}

	//getMeeting tests start here
	@Test
	public void testGetMeetingCorrectIDFuture(){
		contactManagerTester2.addNewContact("Daniel", "Daniel notes");
		contactManagerTester2.addNewContact("Smith", "Smith notes");
		contactManagerTester2.addNewContact("Chris", "Chris notes");
		contacts.add(new ContactImpl("Daniel", "Daniel notes",1));
		contacts.add(new ContactImpl("Smith", "Smith notes",2));
		contacts.add(new ContactImpl("Chris", "Chris notes",3));
		Calendar dateFuture = new GregorianCalendar(2015,12,10);
		Calendar datePast = new GregorianCalendar(2010,12,10);
		contactManagerTester2.addNewPastMeeting(contacts, datePast, "notes"); //meeting id 1
		contactManagerTester2.addFutureMeeting(contacts, dateFuture); 	//meeting id 2
		contactManagerTester2.addFutureMeeting(contacts, dateFuture);	//meeting id 3
		FutureMeetingImpl expectedMeeting = new FutureMeetingImpl(contacts, dateFuture, 2);
		Meeting expected = expectedMeeting;
		Meeting output = contactManagerTester2.getMeeting(2);
		assertEquals(expected, output);
	}
	@Test
	public void testGetMeetingCorrectIDPast(){
		contactManagerTester2.addNewContact("Daniel", "Daniel notes");
		contactManagerTester2.addNewContact("Smith", "Smith notes");
		contactManagerTester2.addNewContact("Chris", "Chris notes");
		contacts.add(new ContactImpl("Daniel", "Daniel notes",1));
		contacts.add(new ContactImpl("Smith", "Smith notes",2));
		contacts.add(new ContactImpl("Chris", "Chris notes",3));
		Calendar dateFuture = new GregorianCalendar(2015,12,10);
		Calendar datePast = new GregorianCalendar(2010,12,10);
		contactManagerTester2.addNewPastMeeting(contacts, datePast, "notes"); //meeting id 1
		contactManagerTester2.addFutureMeeting(contacts, dateFuture); 	//meeting id 2
		contactManagerTester2.addFutureMeeting(contacts, dateFuture);	//meeting id 3
		PastMeetingImpl expectedMeeting = new PastMeetingImpl(contacts, datePast, 1);
		expectedMeeting.addNotes("notes");
		Meeting expected = expectedMeeting;
		Meeting output = contactManagerTester2.getMeeting(1);
		assertEquals(expected, output);
	}
	@Test
	public void testGetMeetingIdDoesNotExist(){
		contactManagerTester2.addNewContact("Daniel", "Daniel notes");
		contactManagerTester2.addNewContact("Smith", "Smith notes");
		contactManagerTester2.addNewContact("Chris", "Chris notes");
		contacts.add(new ContactImpl("Daniel", "Daniel notes",1));
		contacts.add(new ContactImpl("Smith", "Smith notes",2));
		contacts.add(new ContactImpl("Chris", "Chris notes",3));
		Calendar dateFuture = new GregorianCalendar(2015,12,10);
		Calendar datePast = new GregorianCalendar(2010,12,10);
		contactManagerTester2.addNewPastMeeting(contacts, datePast, "notes"); //meeting id 1
		contactManagerTester2.addFutureMeeting(contacts, dateFuture); 	//meeting id 2
		contactManagerTester2.addFutureMeeting(contacts, dateFuture);	//meeting id 3
		MeetingImpl expectedMeeting = null;
		Meeting expected = expectedMeeting;
		contactManagerTester2.getMeeting(4);

	}
}