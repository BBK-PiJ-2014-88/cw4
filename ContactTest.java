import org.junit.*;
import static org.junit.Assert.*;

public class ContactTest{
	private Contact ContactTester1;
	private Contact ContactTester2;
	private Contact ContactTester3;

	@Before
	public void buildUp(){
		contactTester = new ContactImpl();
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