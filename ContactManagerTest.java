import org.junit.*;
import static org.junit.Assert.*;


public class ContactManagerTest{
	private ContactManager contactManagerTester1;
	private ContactManager contactManagerTester2;

	@Before
	public void buildUp(){
		contactManagerTester1 = new ContactManagerImpl();
		contactManagerTester2 = new ContactManagerImpl();
	}

	@Test
	public void firstTest(){
		int expected = 1;
		int output = 1;
		assertEquals(expected,output);
	}
}