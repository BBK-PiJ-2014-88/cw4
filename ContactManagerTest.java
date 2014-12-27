import org.junit.*;
import static org.junit.Assert.*;
import java.util.Set;
import java.util.TreeSet;
import java.util.HashSet;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ArrayList;


public class ContactManagerTest{
	private ContactManager contactManagerTester1;
	private ContactManager contactManagerTester2;
	private ContactManager contactManagerTester3;   //contactManager with 3 Contact's added
	private Set<Contact> contacts; //empty contactSet
	private Set<Contact> contactSet; //contactSet with 3 Contact's added
	private Set<Meeting> meetings;
	Calendar futureDate = new GregorianCalendar(2015,5,12);  //dates that i will use in tests
	Calendar futureDate2 = new GregorianCalendar(2015,10,9);
	Calendar futureDate3 = new GregorianCalendar(2018,1,12);
	Calendar pastDate = new GregorianCalendar(2011,12,12);
	Calendar pastDate2 = new GregorianCalendar(2010,8,12);
	Calendar pastDate3 = new GregorianCalendar(2008,12,30);
	Calendar randomDate = new GregorianCalendar(2014,12,10);

	@Before
	public void buildUp(){
		contactManagerTester1 = new ContactManagerImpl();
		contactManagerTester2 = new ContactManagerImpl();
		contactManagerTester3 = new ContactManagerImpl();
		contacts = new HashSet<Contact>();
		contactSet = new HashSet<Contact>();
		meetings = new TreeSet<Meeting>();
		//Adding 3 contacts to ContactManager3 so exception from unknown_contact is not thrown when contactSet is added with these contacts
		contactManagerTester3.addNewContact("Daniel", "Daniel notes");
		contactManagerTester3.addNewContact("Smith", "Smith notes");
		contactManagerTester3.addNewContact("Chris", "Chris notes");
		contactSet.add(new ContactImpl("Daniel", "Daniel notes",1));  //adding 3 contacts to contact set
		contactSet.add(new ContactImpl("Smith", "Smith notes",2));
		contactSet.add(new ContactImpl("Chris", "Chris notes",3));
	}

	//addNewContact() tests start here
	@Test
	public void testAddNewContact(){
		contactManagerTester1.addNewContact("Simon", "These are tester notes");
		contacts.add(new ContactImpl("Simon", "These are tester notes", 1));
		Set<Contact> expected = contacts;
		Set<Contact> output = contactManagerTester1.getContacts(1);
		assertEquals(expected,output);
	}

	@Test (expected = NullPointerException.class)
	public void testAddNewContactWithNullName(){
			contactManagerTester1.addNewContact(null, "These are tester notes for addNewContactMethod");
	}

	@Test (expected = NullPointerException.class)
	public void testAddNewContactWithNullNotes(){
		contactManagerTester1.addNewContact("Simon", null);
	}

	//getContacts(String) tests start here
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
		contactManagerTester1.addNewContact("Roger Steward", "Roger notes");
		contactManagerTester1.addNewContact("Peter Salazar", "More notes");
		Set<Contact> expectedContactSet = new HashSet<Contact>();
		Set<Contact> expected = expectedContactSet;
		Set<Contact> output = contactManagerTester1.getContacts(new String("zzz"));
		assertEquals(expected,output);
	}

	//getContacts(int) tests start here
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
	public void testGetContactsWithIDIllegalArgumentException(){ //the entered ID does not correspond to any contact
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
		contactManagerTester1.addNewContact("John", "John notes");
		contactManagerTester1.addNewContact("Steven", "Steven notes");
		contactManagerTester1.addNewContact("Stewart", "Stewart notes");
		Set<Contact> contactSetWithUnknown = new HashSet<Contact>();
		contactSetWithUnknown.add(new ContactImpl("John", "John notes", 1));
		contactSetWithUnknown.add(new ContactImpl("Simon", "Simon is not in contact Manager's known contacts", 2));
		contactManagerTester1.addFutureMeeting(contactSetWithUnknown, futureDate);
	}

		@Test (expected = IllegalArgumentException.class) //Adding time in the past as date
		public void testAddFutureMeetingWithPastDate(){
			contactManagerTester3.addFutureMeeting(contactSet, pastDate);
	}

		@Test  //Adding future meeting with correct Parameters. Expect meeting id
		public void testAddFutureMeetingWithCorrectParameter(){
			int expected = 1;
			int output = contactManagerTester3.addFutureMeeting(contactSet, futureDate);
			assertEquals(expected,output);
	}

		@Test  //Adding multiple future meeting with correct Parameters. Expect meeting id
		public void testAddMultipleFutureMeetingWithCorrectParameter(){
			contactManagerTester3.addFutureMeeting(contactSet, futureDate2); //meeting 1
			contactManagerTester3.addFutureMeeting(contactSet, futureDate3); //meeting 2
			int expected = 3;
			int output = contactManagerTester3.addFutureMeeting(contactSet, futureDate);
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
		contactManagerTester1.addNewPastMeeting(contactSetWithUnknown, pastDate, "notes");
	}

	@Test (expected = IllegalArgumentException.class) //adding empty ContactSet as parameter
	public void testAddPastMeetingWithEmptyContactSet(){
		contactManagerTester1.addNewContact("John", "John notes");
		Set<Contact> contactSetEmpty = new HashSet<Contact>();
		contactManagerTester1.addNewPastMeeting(contactSetEmpty, pastDate, "notes");
	}
	@Test (expected = NullPointerException.class) //one of the arguments is null
	public void testAddPastMeetingWithNullContacts(){
		contactManagerTester1.addNewContact("John", "John notes");
		Set<Contact> contactSetNull = null;
		contactManagerTester1.addNewPastMeeting(contactSetNull, pastDate, "notes");
	}
	@Test (expected = NullPointerException.class) //one of the arguments is null
	public void testAddPastMeetingWithNullDate(){
		contactManagerTester1.addNewContact("John", "John notes");
		Set<Contact> contactSet = new HashSet<Contact>();
		contactSet.add(new ContactImpl("John", "John notes", 1));
		Calendar pastDateNull = null;
		contactManagerTester1.addNewPastMeeting(contactSet, pastDateNull, "notes");
	}
	@Test (expected = NullPointerException.class) //one of the arguments is null
	public void testAddPastMeetingWithNullNotes(){
		contactManagerTester1.addNewContact("John", "John notes");
		Set<Contact> contactSet = new HashSet<Contact>();
		contactSet.add(new ContactImpl("John", "John notes", 1));
		contactManagerTester1.addNewPastMeeting(contactSet, pastDate, null);
	}

	@Test  //adding PastMeeting Correctly
	public void testAddPastMeeting(){
		contactManagerTester3.addNewPastMeeting(contactSet, pastDate2, "notes");
		PastMeetingImpl expectedMeeting = new PastMeetingImpl(contactSet, pastDate2, 1);
		expectedMeeting.addNotes("notes");
		Meeting expected = expectedMeeting;
		Meeting output = contactManagerTester3.getPastMeeting(1);
	}


	//getPastMeeting tests start here

	@Test
	public void testGetPastMeeting(){ //correct parameters
		contactManagerTester3.addNewPastMeeting(contactSet, pastDate2, "notes"); //meeting id 1
		contactManagerTester3.addNewPastMeeting(contactSet, pastDate, "notes"); 	//meeting id 2
		contactManagerTester3.addNewPastMeeting(contactSet, futureDate2, "notes");	//meeting id 3
		PastMeetingImpl expectedMeeting = new PastMeetingImpl(contactSet, pastDate, 2);
		expectedMeeting.addNotes("notes");
		PastMeeting expected = expectedMeeting;
		PastMeeting output = contactManagerTester3.getPastMeeting(2);
		assertEquals(expected,output);
	}

	@Test (expected = IllegalArgumentException.class)
	public void testGetPastMeetingFutureDate(){ //input ID for meeting hapenning in future
		contactManagerTester3.addNewPastMeeting(contactSet, pastDate, "notes"); //meeting id 1
		contactManagerTester3.addNewPastMeeting(contactSet, pastDate, "notes"); 	//meeting id 2
		contactManagerTester3.addNewPastMeeting(contactSet, futureDate, "notes");	//has future date, should throw exception
		contactManagerTester3.getPastMeeting(3);
	}

	@Test
	public void testGetPastMeetingIDNonExistant(){ //No meeting with inputted ID
		contactManagerTester3.addNewPastMeeting(contactSet, pastDate, "notes"); //meeting id 1
		contactManagerTester3.addNewPastMeeting(contactSet, pastDate2, "notes"); 	//meeting id 2
		contactManagerTester3.addNewPastMeeting(contactSet, futureDate, "notes");	//meeting id 3
		PastMeeting expectedMeeting = null;
		PastMeeting expected = expectedMeeting;
		PastMeeting output = contactManagerTester3.getPastMeeting(4);
		assertEquals(expected,output);
	}

	//getFutureMeeting tests start here

	@Test
	public void testGetFutureMeeting(){ //correct parameters
		contactManagerTester3.addFutureMeeting(contactSet, futureDate); //meeting id 1
		contactManagerTester3.addFutureMeeting(contactSet, futureDate2); 	//meeting id 2
		contactManagerTester3.addFutureMeeting(contactSet, futureDate3);	//meeting id 3
		FutureMeetingImpl expectedMeeting = new FutureMeetingImpl(contactSet, futureDate2, 2);
		FutureMeeting expected = expectedMeeting;
		FutureMeeting output = contactManagerTester3.getFutureMeeting(2);
		assertEquals(expected,output);
	}

	@Test (expected = IllegalArgumentException.class)
	public void testGetFutureMeetingPastDate(){ //input ID for meeting hapenning in past
		contactManagerTester3.addFutureMeeting(contactSet, futureDate); //meeting id 1
		contactManagerTester3.addFutureMeeting(contactSet, futureDate2); 	//meeting id 2
		contactManagerTester3.addFutureMeeting(contactSet, pastDate);	//has past date, should throw exception
		contactManagerTester3.getFutureMeeting(3);
	}

	@Test
	public void testGetFutureMeetingIDNonExistant(){ //No meeting with inputted ID
		contactManagerTester3.addFutureMeeting(contactSet, futureDate); //meeting id 1
		contactManagerTester3.addFutureMeeting(contactSet, futureDate2); 	//meeting id 2
		contactManagerTester3.addFutureMeeting(contactSet, futureDate3);	//meeting id 3
		FutureMeeting expectedMeeting = null;
		FutureMeeting expected = expectedMeeting;
		FutureMeeting output = contactManagerTester3.getFutureMeeting(4);
		assertEquals(expected,output);
	}

	//getMeeting tests start here

	@Test
	public void testGetMeetingCorrectIDFuture(){  //correctly gets a future Meeting
		contactManagerTester3.addNewPastMeeting(contactSet, pastDate, "notes"); //meeting id 1
		contactManagerTester3.addFutureMeeting(contactSet, futureDate); 	//meeting id 2
		contactManagerTester3.addFutureMeeting(contactSet, futureDate2);	//meeting id 3
		FutureMeetingImpl expectedMeeting = new FutureMeetingImpl(contactSet, futureDate, 2);
		Meeting expected = expectedMeeting;
		Meeting output = contactManagerTester3.getMeeting(2);
		assertEquals(expected, output);
	}
	@Test
	public void testGetMeetingCorrectIDPast(){ //correctly gets a past meeting
		contactManagerTester3.addNewPastMeeting(contactSet, pastDate, "notes"); //meeting id 1
		contactManagerTester3.addFutureMeeting(contactSet, futureDate); 	//meeting id 2
		contactManagerTester3.addFutureMeeting(contactSet, futureDate);	//meeting id 3
		PastMeetingImpl expectedMeeting = new PastMeetingImpl(contactSet, pastDate, 1);
		expectedMeeting.addNotes("notes");
		Meeting expected = expectedMeeting;
		Meeting output = contactManagerTester3.getMeeting(1);
		assertEquals(expected, output);
	}
	@Test
	public void testGetMeetingIdDoesNotExist(){ //tries to get a meeting that doesn't exist
		contactManagerTester3.addNewPastMeeting(contactSet, pastDate, "notes"); //meeting id 1
		contactManagerTester3.addFutureMeeting(contactSet, futureDate); 	//meeting id 2
		contactManagerTester3.addFutureMeeting(contactSet, futureDate);	//meeting id 3
		MeetingImpl expectedMeeting = null;
		Meeting expected = expectedMeeting;
		contactManagerTester3.getMeeting(4);
	}

	//addMeetingNotes tests start here

	@Test
	public void testaddMeetingNotesCorrect(){ //correctly adds meeting notes
		contactManagerTester3.addNewPastMeeting(contactSet, pastDate, "notes:");
		contactManagerTester3.addMeetingNotes(1, "more notes");
		String expected = "notes:" + "\n" + "more notes" + "\n";
		String output = contactManagerTester3.getPastMeeting(1).getNotes();
		assertEquals(expected, output);
	}
	@Test (expected = IllegalArgumentException.class) //adding notes to non-existant meeting
	public void testaddMeetingNotesIDNotExistant(){
		contactManagerTester3.addNewPastMeeting(contactSet, pastDate, "notes:");
		contactManagerTester3.addMeetingNotes(2, "more notes");
	}
	@Test (expected = IllegalArgumentException.class) //adding meeting notes to future meeting
	public void testaddMeetingNotesDateInFuture(){
		contactManagerTester3.addFutureMeeting(contactSet, futureDate);
		contactManagerTester3.addMeetingNotes(1, "more notes");
	}
	@Test (expected = NullPointerException.class) //add null notes to meeting
	public void testaddMeetingNotesNullNotes(){
		contactManagerTester3.addNewPastMeeting(contactSet, pastDate, "notes");
		contactManagerTester3.addMeetingNotes(1, null);
	}

// getFutureMeetingList(Contact) starts here

	@Test
	public void testGetFutureMeetingListCorrect(){ //Correctly returns FutureMeetingList
		Set<Contact> contactSet2 = new HashSet<Contact>();
		contactManagerTester3.addNewContact("Takahito", "Japanese guy");
		contactManagerTester3.addNewContact("Smith", "Smith notes");
		contactSet2.add(new ContactImpl("Takahito", "Japanese guy",4));  //3 contacts have already been added to this contactManager at buildup()
		contactSet2.add(new ContactImpl("Smith", "Smith notes",5));
		contactManagerTester3.addNewPastMeeting(contactSet, pastDate, "notes");
		contactManagerTester3.addNewPastMeeting(contactSet, pastDate, "notes");
		contactManagerTester3.addFutureMeeting(contactSet2, futureDate2); //meeting id 3.
		contactManagerTester3.addFutureMeeting(contactSet2, futureDate); //meeting id 4. futureDate earlier than futureDate2 so should come
		List<Meeting> outputList = new ArrayList();                      //first in returned List
		outputList.add(new FutureMeetingImpl(contactSet2,futureDate , 4));
		outputList.add(new FutureMeetingImpl(contactSet2, futureDate2, 3));
		List<Meeting> expected = outputList;
		List<Meeting> output = contactManagerTester3.getFutureMeetingList(new ContactImpl("Takahito", "Japanese guy", 4));
		assertEquals(expected, output);
	}

	@Test (expected = IllegalArgumentException.class)  //tries to getFutureMeetingList with unknown contact as parameter
	public void testGetFutureMeetingListNonExistantContact(){
		Contact unknownContact = new ContactImpl("Neil", 4);
		contactManagerTester3.getFutureMeetingList(unknownContact);
	}
	@Test //Tries to getFutureMeetingList with known contact but all meetings are in the past
	public void testGetFutureMeetingListAllMeetingsInPast(){
		Set<Contact> contactSet2 = new HashSet<Contact>();
		contactManagerTester3.addNewContact("Takahito", "Japanese guy");
		contactSet2.add(new ContactImpl("Takahito", "Japanese guy",4));
		contactSet2.add(new ContactImpl("Smith", "Smith notes",2));
		contactManagerTester3.addNewPastMeeting(contactSet2, pastDate, "notes");
		contactManagerTester3.addNewPastMeeting(contactSet2, pastDate, "notes");
		contactManagerTester3.addFutureMeeting(contactSet, futureDate);
		contactManagerTester3.addFutureMeeting(contactSet, futureDate2);
		List<Meeting> outputList = new ArrayList();
		List<Meeting> expected = outputList;
		List<Meeting> output = contactManagerTester3.getFutureMeetingList(new ContactImpl("Takahito", "Japanese guy", 4));
		assertEquals(expected, output);
	}

//getFutureMeetingList(Date) tests start here

	@Test //passing a date as parameter on which no meetings took place
	public void testGetFutureMeetingListDateWithNoMeetings(){
		contactManagerTester3.addNewPastMeeting(contactSet, pastDate, "notes");
		contactManagerTester3.addFutureMeeting(contactSet, futureDate);
		List<Meeting> emptyMeetingList = new ArrayList<Meeting>();
		Calendar dateWithNoMeeting = new GregorianCalendar(2019,5,2);
		List<Meeting> expected = emptyMeetingList;
		List<Meeting> output = contactManagerTester3.getFutureMeetingList(dateWithNoMeeting);
		assertEquals(expected, output);
	}

	@Test //Correct Parameters passed. There are several meetings taking place on same date. Returned List is Arranged chronologically by time
	public void testGetFutureMeetingListSameDateDifferentTimes(){
		Calendar sameDayDifferentTime1 = new GregorianCalendar(2017, 11, 11, 12, 10);
		Calendar sameDayDifferentTime2 = new GregorianCalendar(2017, 11, 11, 15, 10);
		Calendar sameDayDifferentTime3 = new GregorianCalendar(2017, 11, 11, 15, 30);
		Calendar sameDayDifferentTime4 = new GregorianCalendar(2017, 11, 11, 18, 10);
		contactManagerTester3.addFutureMeeting(contactSet, sameDayDifferentTime3); //added in random order
		contactManagerTester3.addFutureMeeting(contactSet, sameDayDifferentTime1); // returned list should be in order
		contactManagerTester3.addFutureMeeting(contactSet, sameDayDifferentTime4);
		contactManagerTester3.addFutureMeeting(contactSet, sameDayDifferentTime2);
		contactManagerTester3.addNewPastMeeting(contactSet, pastDate, "notes"); //different date, this meeting should not be in output
		List<Meeting> ChronologicallySortedMeetingList = new ArrayList<Meeting>();
		ChronologicallySortedMeetingList.add(new FutureMeetingImpl(contactSet, sameDayDifferentTime1, 2));
		ChronologicallySortedMeetingList.add(new FutureMeetingImpl(contactSet, sameDayDifferentTime2, 4));
		ChronologicallySortedMeetingList.add(new FutureMeetingImpl(contactSet, sameDayDifferentTime3, 1));
		ChronologicallySortedMeetingList.add(new FutureMeetingImpl(contactSet, sameDayDifferentTime4, 3));
		List<Meeting> expected = ChronologicallySortedMeetingList;
		List<Meeting> output = contactManagerTester3.getFutureMeetingList(new GregorianCalendar(2017,11,11));
		assertEquals(expected, output);
	}

	@Test //Testing that this method can also correctly return a meeting with a future date
	public void testGetFutureMeetingListMeetingWithFutureDate(){
		contactManagerTester3.addFutureMeeting(contactSet, futureDate2);
		List<Meeting> listWithFutureDate = new ArrayList<Meeting>();
		listWithFutureDate.add(new FutureMeetingImpl(contactSet, futureDate2, 1));
		List<Meeting> expected = listWithFutureDate;
		List<Meeting> output = contactManagerTester3.getFutureMeetingList(futureDate2);
		assertEquals(expected, output);
	}

//getPastMeetingList(Contact) tests start here

	@Test (expected = IllegalArgumentException.class)
	public void testGetPastMeetingListUnknownContact(){ //Inserting unknown contact as parameter to getPastMeetingList(Contact contact)
		contactManagerTester3.addNewPastMeeting(contactSet, pastDate, "notes");
		contactManagerTester3.getPastMeetingList(new ContactImpl("Suzuki", "Suzuki notes", 8));
	}

	@Test //Contact passed in parameter is valid but all contact's meetings are in the future so empty List is returned
	public void testGetPastMeetingListMeetingsAllInFuture(){
		contactManagerTester3.addFutureMeeting(contactSet, futureDate);
		contactManagerTester3.addFutureMeeting(contactSet, futureDate2);
		contactManagerTester3.addFutureMeeting(contactSet, futureDate3);
		contactManagerTester3.addFutureMeeting(contactSet, futureDate);
		List<PastMeeting> EmptyMeetingList = new ArrayList<PastMeeting>();
		List<PastMeeting> expected = EmptyMeetingList;
		List<PastMeeting> output = contactManagerTester3.getPastMeetingList(new ContactImpl("Smith", "Smith notes",2));
		assertEquals(expected, output);
	}

	@Test //Correct Parameters passed. There are several meetings taking place on same date. Returned list is Arranged chronologically by time
	public void testGetPastMeetingListSameDateDifferentTimes(){
		Calendar sameDayDifferentTime1 = new GregorianCalendar(2011, 8, 11, 12, 10);
		Calendar sameDayDifferentTime2 = new GregorianCalendar(2011, 8, 11, 15, 10);
		Calendar sameDayDifferentTime3 = new GregorianCalendar(2011, 8, 11, 15, 30);
		Calendar sameDayDifferentTime4 = new GregorianCalendar(2011, 8, 11, 19, 10);
		contactManagerTester3.addNewPastMeeting(contactSet, sameDayDifferentTime3, "notes"); //added in random order
		contactManagerTester3.addNewPastMeeting(contactSet, sameDayDifferentTime1, "notes"); //returned list should be in order
		contactManagerTester3.addNewPastMeeting(contactSet, sameDayDifferentTime4, "notes");
		contactManagerTester3.addNewPastMeeting(contactSet, sameDayDifferentTime2, "notes");
		List<PastMeeting> ChronologicallySortedPastMeetingList = new ArrayList<PastMeeting>();
		PastMeetingImpl pastMeeting1 = new PastMeetingImpl(contactSet, sameDayDifferentTime1, 2);
		pastMeeting1.addNotes("notes");
		PastMeetingImpl pastMeeting2 = new PastMeetingImpl(contactSet, sameDayDifferentTime2, 4);
		pastMeeting2.addNotes("notes");
		PastMeetingImpl pastMeeting3 = new PastMeetingImpl(contactSet, sameDayDifferentTime3, 1);
		pastMeeting3.addNotes("notes");
		PastMeetingImpl pastMeeting4 = new PastMeetingImpl(contactSet, sameDayDifferentTime4, 3);
		pastMeeting4.addNotes("notes");
		ChronologicallySortedPastMeetingList.add(pastMeeting1);
		ChronologicallySortedPastMeetingList.add(pastMeeting2);
		ChronologicallySortedPastMeetingList.add(pastMeeting3);
		ChronologicallySortedPastMeetingList.add(pastMeeting4);
		List<PastMeeting> expected = ChronologicallySortedPastMeetingList;
		List<PastMeeting> output = contactManagerTester3.getPastMeetingList(new ContactImpl("Smith", "Smith notes",2));
		assertEquals(expected, output);
	}

}