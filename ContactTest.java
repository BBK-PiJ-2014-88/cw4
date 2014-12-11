import org.junit.*;
import static org.junit.Assert.*;

public class ContactTest{
	private Contact contactTester1;
	private Contact contactTester2;
	private Contact contactTester3;

	@Before
	public void buildUp(){
		contactTester1 = new ContactImpl();
		contactTester2 = new ContactImpl();
		contactTester3 = new ContactImpl();
	}

	@Test
	public void testReturnCorrectID(){
		int expected = 3;
		int output = contactTester3.getId();
		assertEquals(output,expected);
	}
}