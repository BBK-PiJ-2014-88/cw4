import org.junit.*;
import static org.junit.Assert.*;

public class MeetingTest{
	private Meeting meetingTester1;
	private Meeting meetingTester2;
	private Meeting meetingTester3;

	@Before
	public void buildUp(){
		meetingTester1 = new MeetingImpl();
		meetingTester2 = new MeetingImpl();
		meetingTester3 = new MeetingImpl();
	}

	@Test
	public void testFirstContactReturnCorrectID(){
		int expected = 1;
		int output = meetingTester1.getId();
		assertEquals(expected,output);
	}
}