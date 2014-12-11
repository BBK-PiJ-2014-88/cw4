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
	public void testFirstContactReturnCorrectID(){
		int expected = 1;
		int output = contactTester1.getId();
		assertEquals(expected,output);
	}

	@Test
	public void testReturnCorrectID(){
		int expected = 6; //3 contact instances were created in 1st test, another 3 in this test, so this is the 6th object so will have an id of 6
		int output = contactTester3.getId();
		assertEquals(expected, output);
	}

	@Test
	public void testReturnCorrectNotes(){
		contactTester1.addNotes("Notes for Testing");
		String expected = "Notes for Testing";
		String output = contactTester1.getNotes();
		assertEquals(expected,output);
	}



}