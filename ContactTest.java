import org.junit.*;
import static org.junit.Assert.*;

public class ContactTest{
	private Contact contactTester1;
	private Contact contactTester2;
	private Contact contactTester3;
	private Contact contactTester4;

	@Before
	public void buildUp(){
		contactTester1 = new ContactImpl("",1);
		contactTester2 = new ContactImpl("Steven",2);
		contactTester3 = new ContactImpl("",3);
		contactTester4 = new ContactImpl("John", " is cool", 4);
	}

	@Test
	public void testFirstContactReturnCorrectID(){
		int expected = 1;
		int output = contactTester1.getId();
		assertEquals(expected,output);
	}

	@Test
	public void testReturnCorrectID(){
		int expected = 3;
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

	@Test
	public void testReturnCorrectName(){
		String expected = "Steven";
		String output = contactTester2.getName();
		assertEquals(expected, output);
	}

	@Test
	public void testBothParameters(){
		String expected = "John is cool";
		String output = contactTester4.getName() + contactTester4.getNotes();
		assertEquals(expected, output);
	}

	// Later on, need to add test where new contact is created with same ID as another. Throws error as Id's must be unique.

}