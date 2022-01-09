import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Bet;
import domain.Event;
import domain.Forecast;
import domain.Question;
import domain.RegularUser;
import domain.User;

class CreateBetDATest {

	static DataAccess sut = new DataAccess(ConfigXML.getInstance().getDataBaseOpenMode().equals("initialize"));;
	static DataAccess testDA = new DataAccess(ConfigXML.getInstance().getDataBaseOpenMode().equals("initialize"));
	private Event ev;

//	@BeforeAll
//	static void setUpBeforeClass() throws Exception {
//		testDA.open(false);
//		testDA.registrar("usuario", "Usuario1?", "Usuario", "Apellido", "01/01/1997", "usuario@gmail.com", "ES11 1111 1111 1111", 123456789, "", 100);
//		testDA.close();
//	}
	@Test
	@DisplayName("Bet negative value")
	void test1() {
		try {
			User u = new RegularUser("usuario", "Usuario1?", "Usuario", "Apellido", "01/01/1997", "usuario@gmail.com", "ES11 1111 1111 1111", 123456789, "", 100);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate = sdf.parse("05/10/2022");
			String eventText = "Event Text 1";
			Event e = new Event(eventText, oneDate);
			ev = e;
			testDA.open(false);
			testDA.insertEvent(e);
			Question q = testDA.createQuestion(e, "Query Text", 2);
			Forecast f = new Forecast("f", 1, q);
			testDA.insertForecast(q, "f", 1);
			testDA.close();

			Bet b = new Bet(f, u, -2);

			int obtained = sut.createBet((RegularUser) u, f, b);


			assertEquals(1, obtained);

		} catch (Exception e) {
			fail("Error");
		}

		testDA.open(false);
		boolean b = testDA.deleteEvent(ev);
		System.out.println("Removed event " + b);
		testDA.close();
	}

	@Test
	@DisplayName("Less than minimum amount")
	void test2() {
		try {
			User u = new RegularUser("usuario", "Usuario1?", "Usuario", "Apellido", "01/01/1997", "usuario@gmail.com", "ES11 1111 1111 1111", 123456789, "", 100);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate = sdf.parse("05/10/2022");
			String eventText = "Event Text 2";
			Event e = new Event(eventText, oneDate);
			ev = e;
			
			testDA.open(false);
			testDA.insertEvent(e);
			Question q = testDA.createQuestion(e, "Query Text", 2);
			Forecast f = new Forecast("f", 1, q);
			testDA.insertForecast(q, "f", 1);
			testDA.close();

			Bet b = new Bet(f, u, 1);

			int obtained = sut.createBet((RegularUser) u, f, b);


			assertEquals(2, obtained);

		} catch (Exception e) {
			fail("Error");
		}

		testDA.open(false);
		boolean b = testDA.deleteEvent(ev);
		System.out.println("Removed event " + b);
		testDA.close();
	}

	@Test
	@DisplayName("User not enougth money")
	void test3() {
		try {
			User u = new RegularUser("usuario", "Usuario1?", "Usuario", "Apellido", "01/01/1997", "usuario@gmail.com", "ES11 1111 1111 1111", 123456789, "", 1);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate = sdf.parse("05/10/2022");
			String eventText = "Event Text 3";
			Event e = new Event(eventText, oneDate);
			ev = e;
			
			testDA.open(false);
			testDA.insertEvent(e);
			Question q = testDA.createQuestion(e, "Query Text", 2);
			Forecast f = new Forecast("f", 1, q);
			testDA.insertForecast(q, "f", 1);
			

			Bet b = new Bet(f, u, 11);

			int obtained = sut.createBet((RegularUser) u, f, b);

			testDA.close();
			
			assertEquals(3, obtained);

		} catch (Exception e) {
			fail("Error");
		}

		testDA.open(false);
		boolean b = testDA.deleteEvent(ev);
		System.out.println("Removed event " + b);
		testDA.close();
	}

	@Test
	@DisplayName("Bet created")
	void test4() {
		try {
			User u = new RegularUser("usuario", "Usuario1?", "Usuario", "Apellido", "01/01/1997", "usuario@gmail.com", "ES11 1111 1111 1111", 123456789, "", 100);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate = sdf.parse("05/10/2022");
			String eventText = "Event Text 4";
			Event e = new Event(eventText, oneDate);
			ev = e;
			
			testDA.open(false);
			testDA.insertEvent(e);
			Question q = testDA.createQuestion(e, "Query Text", 2);
			Forecast f = new Forecast("f", 1, q);
			testDA.insertForecast(q, "f", 1);
			

			Bet b = new Bet(f, u, 2);

			int obtained = sut.createBet((RegularUser) u, f, b);
			testDA.close();

			assertEquals(5, obtained);

		} catch (Exception e) {
			fail("Error");
		}

		testDA.open(false);
		boolean b = testDA.deleteEvent(ev);
		System.out.println("Removed event " + b);
		testDA.close();
	}

	@Test
	@DisplayName("DB error")
	void test5() {
		try {
			User u = new RegularUser("usuario", "Usuario1?", "Usuario", "Apellido", "01/01/1997", "usuario@gmail.com", "ES11 1111 1111 1111", 123456789, "", 100);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate = sdf.parse("05/10/2022");
			String eventText = "Event Text 5";
			Event e = new Event(eventText, oneDate);
			ev = e;
			
			testDA.open(false);
			testDA.insertEvent(e);
			Question q = testDA.createQuestion(e, "Query Text", 2);
			Forecast f = new Forecast("f", 1, q);
			testDA.close();

			Bet b = new Bet(f, u, 2);

			int obtained = sut.createBet((RegularUser) u, f, b);


			assertEquals(5, obtained);

		} catch (Exception e) {
			fail("Error");
		}

		testDA.open(false);
		boolean b = testDA.deleteEvent(ev);
		System.out.println("Removed event " + b);
		testDA.close();
	}
}
