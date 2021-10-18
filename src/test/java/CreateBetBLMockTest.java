import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;
import domain.Bet;
import domain.Event;
import domain.Forecast;
import domain.Question;
import domain.RegularUser;
import domain.User;
import exceptions.QuestionAlreadyExist;

class CreateBetBLMockTest {
	DataAccess dataAccess = Mockito.mock(DataAccess.class);
	Event mockedEvent = Mockito.mock(Event.class);
	BLFacade sut = new BLFacadeImplementation(dataAccess);

	@Test
	@DisplayName("Bet negative value")
	void test1() {
		try {
			RegularUser u = new RegularUser("usuario", "Usuario1?", "Usuario", "Apellido", "01/01/1997", "usuario@gmail.com", "ES11 1111 1111 1111", 123456789, "", 0);
			Question q = new Question("q", 2, mockedEvent);
			Forecast f = new Forecast("f", 1, q);
			Bet b = new Bet(f, u, -2); 
			
			Mockito.doReturn(u).when(dataAccess).registrar("usuario", "Usuario1?", "Usuario", "Apellido", "01/01/1997", "usuario@gmail.com", "ES11 1111 1111 1111", 123456789, "", 0);
			Mockito.when(dataAccess.createBet(u, f, b)).thenReturn(1);
			sut.registrar("usuario", "Usuario1?", "Usuario", "Apellido", "01/01/1997", "usuario@gmail.com", "ES11 1111 1111 1111", 123456789, "", 0);
			sut.insertForecast(q, "f", 1);
			int obtained = sut.createBet(u, f, b);
			
						
			assertEquals(1, obtained);
			
		} catch (Exception e) {
			fail("Error");
		}
	}
	
	@Test
	@DisplayName("Less than minimum amount")
	void test2() {
		try {
			RegularUser u = new RegularUser("usuario", "Usuario1?", "Usuario", "Apellido", "01/01/1997", "usuario@gmail.com", "ES11 1111 1111 1111", 123456789, "", 0);
			Question q = new Question("q", 2, mockedEvent);
			Forecast f = new Forecast("f", 1, q);
			Bet b = new Bet(f, u, 1);
			
			Mockito.doReturn(u).when(dataAccess).registrar("usuario", "Usuario1?", "Usuario", "Apellido", "01/01/1997", "usuario@gmail.com", "ES11 1111 1111 1111", 123456789, "", 0);
			Mockito.when(dataAccess.createBet(u, f, b)).thenReturn(2);
			sut.registrar("usuario", "Usuario1?", "Usuario", "Apellido", "01/01/1997", "usuario@gmail.com", "ES11 1111 1111 1111", 123456789, "", 0);
			sut.insertForecast(q, "f", 1);
			int obtained = sut.createBet(u, f, b);
			
						
			assertEquals(2, obtained);
			
		} catch (Exception e) {
			fail("Error");
		}
	}
	
	@Test
	@DisplayName("User not enougth money")
	void test3() {
		try {
			RegularUser u = new RegularUser("usuario", "Usuario1?", "Usuario", "Apellido", "01/01/1997", "usuario@gmail.com", "ES11 1111 1111 1111", 123456789, "", 0);
			Question q = new Question("q", 2, mockedEvent);
			Forecast f = new Forecast("f", 1, q);
			Bet b = new Bet(f, u, 4);
			
			Mockito.doReturn(u).when(dataAccess).registrar("usuario", "Usuario1?", "Usuario", "Apellido", "01/01/1997", "usuario@gmail.com", "ES11 1111 1111 1111", 123456789, "", 0);
			Mockito.doReturn(f).when(dataAccess).insertForecast(q, "f", 1);
			Mockito.when(dataAccess.createBet(u, f, b)).thenReturn(3);
			sut.registrar("usuario", "Usuario1?", "Usuario", "Apellido", "01/01/1997", "usuario@gmail.com", "ES11 1111 1111 1111", 123456789, "", 0);
			sut.insertForecast(q, "f", 1);
			int obtained = sut.createBet(u, f, b);
			
						
			assertEquals(3, obtained);
			
		} catch (Exception e) {
			fail("Error");
		}
	}
	
	@Test
	@DisplayName("Bet created")
	void test4() {
		try {
			RegularUser u = new RegularUser("usuario", "Usuario1?", "Usuario", "Apellido", "01/01/1997", "usuario@gmail.com", "ES11 1111 1111 1111", 123456789, "", 10);
			Question q = new Question("q", 2, mockedEvent);
			Forecast f = new Forecast("f", 1, q);
			Bet b = new Bet(f, u, 4); 
			
			Mockito.doReturn(u).when(dataAccess).registrar("usuario", "Usuario1?", "Usuario", "Apellido", "01/01/1997", "usuario@gmail.com", "ES11 1111 1111 1111", 123456789, "", 0);
			Mockito.doReturn(f).when(dataAccess).insertForecast(q, "f", 1);
			Mockito.when(dataAccess.createBet(u, f, b)).thenReturn(4);
			sut.registrar("usuario", "Usuario1?", "Usuario", "Apellido", "01/01/1997", "usuario@gmail.com", "ES11 1111 1111 1111", 123456789, "", 0);
			sut.insertForecast(q, "f", 1);
			int obtained = sut.createBet(u, f, b);
			
						
			assertEquals(4, obtained);
			
		} catch (Exception e) {
			fail("Error");
		}
	}
	
	@Test
	@DisplayName("DB error")
	void test5() {
		try {
			RegularUser u = new RegularUser("usuario", "Usuario1?", "Usuario", "Apellido", "01/01/1997", "usuario@gmail.com", "ES11 1111 1111 1111", 123456789, "", 10);
			Question q = new Question("q", 2, mockedEvent);
			Forecast f = new Forecast("f", 1, q);
			Bet b = new Bet(f, u, 4); 
			
			Mockito.doReturn(u).when(dataAccess).registrar("usuario", "Usuario1?", "Usuario", "Apellido", "01/01/1997", "usuario@gmail.com", "ES11 1111 1111 1111", 123456789, "", 0);
			Mockito.when(dataAccess.createBet(u, f, b)).thenReturn(5);
			sut.registrar("usuario", "Usuario1?", "Usuario", "Apellido", "01/01/1997", "usuario@gmail.com", "ES11 1111 1111 1111", 123456789, "", 0); 	
			int obtained = sut.createBet(u, f, b);
			
						
			assertEquals(5, obtained);
			
		} catch (Exception e) {
			fail("Error");
		}
	}

}
