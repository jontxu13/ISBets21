package businessLogic;

import java.util.ArrayList;
import java.util.Calendar;
//hola
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;

import configuration.ConfigXML;
import configuration.UtilDate;
import dataAccess.DataAccess;
import domain.AdminUser;
import domain.Bet;
import domain.Event;
import domain.Forecast;
import domain.Question;
import domain.RegularUser;
import domain.User;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;
import exceptions.UserAlreadyExistException;

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation implements BLFacade {
	DataAccess dbManager;
	String ini = "initialize";
	public BLFacadeImplementation() {
		System.out.println("Creating BLFacadeImplementation instance");
		ConfigXML c = ConfigXML.getInstance();

		if (c.getDataBaseOpenMode().equals(ini)) {
			dbManager = new DataAccess(c.getDataBaseOpenMode().equals(ini));
			dbManager.initializeDB();
			dbManager.close();
		}

	}

	public BLFacadeImplementation(DataAccess da) {

		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		ConfigXML c = ConfigXML.getInstance();

		if (c.getDataBaseOpenMode().equals(ini)) {
			da.open(true);
			da.initializeDB();
			da.close();

		}
		dbManager = da;
	}

	/**
	 * This method creates a question for an event, with a question text and the
	 * minimum bet
	 * 
	 * @param event      to which question is added
	 * @param question   text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws EventFinished        if current data is after data of the event
	 * @throws QuestionAlreadyExist if the same question already exists for the
	 *                              event
	 */
	@Override
	@WebMethod
	public Question createQuestion(Event event, String question, float betMinimum)
			throws EventFinished, QuestionAlreadyExist {

		// The minimum bed must be greater than 0
		dbManager.open(false);
		Question qry = null;

		if (new Date().compareTo(event.getEventDate()) > 0)
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));

		qry = dbManager.createQuestion(event, question, betMinimum);

		dbManager.close();

		return qry;
	};

	@Override
	public ArrayList<Question> getAllQuestions() {
		dbManager.open(false);
		ArrayList<Question> questions = dbManager.getAllQuestions();
		dbManager.close();
		return questions;
	}

	@Override
	public boolean deleteEvent(Event evento) {
		dbManager.open(false);
		boolean res = dbManager.deleteEvent(evento);
		dbManager.close();
		return res;
	}

	/**
	 * This method invokes the data access to retrieve the events of a given date
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@Override
	@WebMethod
	public ArrayList<Event> getEvents(Date date) {
		dbManager.open(false);
		ArrayList<Event> events = dbManager.getEvents(date);
		dbManager.close();
		return events;
	}

	/**
	 * This method invokes the data access to retrieve the events of a given date
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@Override
	@WebMethod
	public ArrayList<Event> getAllEvents() {
		dbManager.open(false);
		ArrayList<Event> events = dbManager.getAllEvents();
		dbManager.close();
		return events;
	}

	/**
	 * This method invokes the data access to retrieve the dates a month for which
	 * there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	@Override
	@WebMethod
	public ArrayList<Date> getEventsMonth(Date date) {
		dbManager.open(false);
		ArrayList<Date> dates = dbManager.getEventsMonth(date);
		dbManager.close();
		return dates;
	}

	public void close() {
		DataAccess dB4oManager = new DataAccess(false);

		dB4oManager.close();

	}

	/**
	 * This method invokes the data access to initialize the database with some
	 * events and questions. It is invoked only when the option "initialize" is
	 * declared in the tag dataBaseOpenMode of resources/config.xml file
	 */
	@Override
	@WebMethod
	public void initializeBD() {
		dbManager.open(false);
		dbManager.initializeDB();
		dbManager.close();
	}

//	@Override
//	public RegularUser login(String userName, String password)
//			throws UserDoesNotExistException, IncorrectPassException {
//		dbManager.open(false);
//		User login = dbManager.login(userName, password);
//		dbManager.close();
//		return login;
//	}

//	public boolean validoUsuario(String puser) {
//		dbManager.open(false);
//		boolean usuarioBD = dbManager.validoUsuario(puser);
//		dbManager.close();
//		return usuarioBD;
//	}

	@Override
	public RegularUser registrar(String user, String pass, String name, String lastName, String birthDate, String email,
			String account, Integer numb, String address, float balance) throws UserAlreadyExistException {
		dbManager.open(false);
		RegularUser u = dbManager.registrar(user, pass, name, lastName, birthDate, email, account, numb, address,
				balance);
		dbManager.close();
		return u;
	}

	@Override
	public boolean insertEvent(Event pEvento) {
		dbManager.open(false);
		boolean inserted = dbManager.insertEvent(pEvento);
		dbManager.close();

		return inserted;
	}

	@Override
	public int getNumberEvents() {
		dbManager.open(false);
		int n = dbManager.getNumberEvents();
		dbManager.close();
		return n;
	}

	@Override
	public boolean existEvent(Event event) {
		dbManager.open(false);
		boolean result = dbManager.existEvent(event);
		dbManager.close();
		return result;
	}

	@Override
	public int getNumberForecasts() {
		dbManager.open(false);
		int n = dbManager.getNumberForecasts();
		dbManager.close();
		return n;
	}

	@Override
	public boolean existForecast(Forecast f) {
		dbManager.open(false);
		boolean result = dbManager.existForecast(f);
		dbManager.close();
		return result;
	}

	@Override
	public boolean insertForecast(Question question, String forecast, float fee) {
		dbManager.open(false);
		Forecast inserted = dbManager.insertForecast(question, forecast, fee);
		if (inserted == null) {
			return false;
		}
		dbManager.close();
		return true;
	}

	@Override
	public ArrayList<Forecast> getForecasts() {
		dbManager.open(false);
		ArrayList<Forecast> result = dbManager.getForecasts();
		dbManager.close();
		return result;
	}

	@Override
	public ArrayList<Forecast> getForecasts(Question pregunta) {
		dbManager.open(false);
		ArrayList<Forecast> result = dbManager.getForecasts(pregunta);
		dbManager.close();
		return result;
	}

	@Override
	public boolean editarPerfilUsuario(String pContraseña, String pUsername, String pNombre, String pApellido,
			String pEmail, String pCuentaBancaria) {

		dbManager.open(false);

		boolean res = dbManager.editarPerfilUsuario(pContraseña, pUsername, pNombre, pApellido, pEmail,
				pCuentaBancaria);
		dbManager.close();

		return res;

	}

	@Override
	public boolean editarPerfilUsuarioSinPass(String pUsername, String pNombre, String pApellido, String pEmail,
			String pCuentaBancaria) {

		dbManager.open(false);

		boolean res = dbManager.editarPerfilUsuarioSinPass(pUsername, pNombre, pApellido, pEmail, pCuentaBancaria);
		dbManager.close();

		return res;

	}

	@Override
	public ArrayList<User> getAllUsers() {

		dbManager.open(false);

		ArrayList<User> users = dbManager.getAllUsers();
		dbManager.close();
		return users;

	}

	@Override
	public Integer getMaxIdInDB() {

		dbManager.open(false);
		Integer maxid = dbManager.getMaxIdInDB();
		dbManager.close();
		return maxid;

	}

	@Override
	public boolean doLogin(String username, String pass) {
		dbManager.open(false);
		boolean bo = dbManager.doLogin(username, pass);
		dbManager.close();
		return bo;
	}

	@Override
	public boolean isAdmin(String pusername, String ppassword) {
		dbManager.open(false);
		boolean bo = dbManager.isAdmin(pusername, ppassword);
		dbManager.close();
		return bo;
	}

	@Override
	public RegularUser getRegularUserByUsername(String pusername) {
		dbManager.open(false);
		RegularUser ru = dbManager.getRegularUserByUsername(pusername);
		dbManager.close();
		return ru;
	}

	@Override
	public AdminUser getAdminUserByUsername(String pusername) {
		dbManager.open(false);
		AdminUser au = dbManager.getAdminUserByUsername(pusername);
		dbManager.close();
		return au;

	}

	@Override
	public int crearApuesta(RegularUser u, Forecast f, Bet b) {
		dbManager.open(false);
		int inserted = dbManager.createBet(u, f, b);
		return inserted;
	}

	@Override
	public boolean closeEvent(Event e, Question q, Forecast f) {
		dbManager.open(false);
		boolean closed = dbManager.closeEvent(e, q, f);
		return closed;
	}

	public boolean anularApuesta(Bet pApuesta) {

		dbManager.open(false);

		boolean result = dbManager.anularApuesta(pApuesta);
		return result;

	}
	
	public boolean cancelEvent(Event e) {
		dbManager.open(false);
		
		boolean result = dbManager.cancelEvent(e);
		return result;
	}
	
	public boolean addMoney(RegularUser u, float n) {
		dbManager.open(false);
		boolean result = dbManager.addMoney(u, n);
		return result;
	}

	public static void main(String[] args) {

		BLFacadeImplementation controlador = new BLFacadeImplementation();
		RegularUser usuario = new RegularUser("usuario", "Usuario1?", "Nombre", "Apellido", "01/01/2000",
				"usuario@gmail.com", "ES11 1111 1111 1111", 123456789, "", 0);
		Event ev1 = new Event(69, "Eibar-Eibar", UtilDate.newDate(2015, 4, 17));
		Question pregunta = new Question("pregunta", 2, ev1);
		Forecast pronostico = new Forecast("Madrid", 17, pregunta);
		Bet apuesta = new Bet(pronostico, usuario, 13);

		System.out.println(controlador.anularApuesta(apuesta));

	}

}
