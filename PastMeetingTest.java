import org.junit.*;
import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.HashSet;


public class PastMeetingTest{
	private PastMeeting pastMeetingTester1;
	private PastMeeting pastMeetingTester2;
	private PastMeeting pastMeetingTester3;
	private Set<Meeting> pastMeetingTesterSet = new TreeSet<Meeting>();

	@Before
	public void buildUp(){
		pastMeetingTester1 = new PastMeetingImpl("");
		pastMeetingTester2 = new PastMeetingImpl("Steven");
		pastMeetingTester3 = new PastMeetingImpl("");
		Calendar date = new GregorianCalendar(2014,12,05);
		Calendar oldDate = new GregorianCalendar(2012,05,04);
		contactTesterSet.add(new ContactImpl("John"));
		contactTesterSet.add(new ContactImpl("Freddy"));
		contactTesterSet.add(new ContactImpl("Xavier"));
	}