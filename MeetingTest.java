import org.junit.*;
import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;

public class MeetingTest{
	private Meeting meetingTester1;
	private Meeting meetingTester2;
	private Meeting meetingTester3;
	private Set<Contact> contactTesterSet;

	@Before
	public void buildUp(){
		Calendar date = new GregorianCalendar(2014,12,05);
		Calendar oldDate = new GregorianCalendar(2012,05,04);
		contactTesterSet.add(new ContactImpl("John"));
		contactTesterSet.add(new ContactImpl("Freddy"));
		contactTesterSet.add(new ContactImpl("Xavier"));
		meetingTester1 = new MeetingImpl(contactTesterSet,date);
		meetingTester2 = new MeetingImpl(contactTesterSet,oldDate);
	}

	@Test
	public void testGetDate(){
		Calendar expected = new GregorianCalendar(2014,12,05);
		Calendar output = meetingTester1.getDate();
		assertEquals(expected,output);
	}

	@Test
	public void testGetDateSecond(){
		Calendar expected = new GregorianCalendar(2012,05,04);
		Calendar output = meetingTester2.getDate();
		assertEquals(expected,output);
	}
	@Test
	public void testGetContacts(){
		Set<Contact> expected = contactTesterSet;
		Set<Contact> output = meetingTester2.getContacts();
		assertEquals(expected,output);
	}


}