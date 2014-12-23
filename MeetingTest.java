import org.junit.*;
import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.HashSet;

public class MeetingTest{
	private Meeting meetingTester1;
	private Meeting meetingTester2;
	private Meeting meetingTester3;
	private Set<Contact> contactTesterSet = new HashSet<Contact>();

	@Before
	public void buildUp(){
		Calendar date = new GregorianCalendar(2014,12,05);
		Calendar oldDate = new GregorianCalendar(2012,05,04);
		contactTesterSet.add(new ContactImpl("John",1));
		contactTesterSet.add(new ContactImpl("Freddy",2));
		contactTesterSet.add(new ContactImpl("Xavier",3));
		meetingTester1 = new MeetingImpl(contactTesterSet,date,1);
		meetingTester2 = new MeetingImpl(contactTesterSet,oldDate,2);
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