import org.junit.*;
import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MeetingTest{
	private Meeting meetingTester1;
	private Meeting meetingTester2;
	private Meeting meetingTester3;

	@Before
	public void buildUp(){
		Calendar date = new GregorianCalendar(2014,12,05);
		Calendar oldDate = new GregorianCalendar(2012,05,04);
		meetingTester1 = new MeetingImpl(date);
		meetingTester2 = new MeetingImpl(oldDate);
		meetingTester3 = new MeetingImpl(date);
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
}