package dataAccess;

import java.util.ArrayList;
//hello
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import businessLogic.BLFacadeImplementation;
import configuration.ConfigXML;
import configuration.UtilDate;
import domain.AdminUser;
import domain.Bet;
import domain.Event;
import domain.Forecast;
import domain.Question;
import domain.RegularUser;
import domain.User;
import exceptions.IncorrectPassException;
import exceptions.QuestionAlreadyExist;
import exceptions.UserAlreadyExistException;
import exceptions.UserDoesNotExistException;

/**
 * It implements the data access to the objectDb database 
 */
public class DataAccessDaSilva2 {
	protected static EntityManager db;
	protected static EntityManagerFactory emf;
	static String adm = "admin";

	ConfigXML c = ConfigXML.getInstance();

	public DataAccessDaSilva2(boolean initializeMode) {

		System.out.println("Creating DataAccess instance => isDatabaseLocal: " + c.isDatabaseLocal()
		+ " getDatabBaseOpenMode: " + c.getDataBaseOpenMode());

		open(initializeMode);

	}

	public DataAccessDaSilva2() {
		new DataAccessDaSilva2(false);
	}

	/**
	 * This is the data access method that initializes the database with some events
	 * and questions. This method is invoked by the business logic (constructor of
	 * BLFacadeImplementation) when the option "initialize" is declared in the tag
	 * dataBaseOpenMode of resources/config.xml file
	 */
	public void initializeDB() {

		db.getTransaction().begin();
		try {

			Calendar today = Calendar.getInstance();

			int month = today.get(Calendar.MONTH);
			month += 1;
			int year = today.get(Calendar.YEAR);
			if (month == 12) {
				month = 0;
				year += 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method creates a question for an event, with a question text and the
	 * minimum bet
	 * 
	 * @param event      to which question is added
	 * @param question   text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws QuestionAlreadyExist if the same question already exists for the
	 *                              event
	 */
	public Question createQuestion(Event event, String question, float betMinimum) throws QuestionAlreadyExist {
		System.out.println(">> DataAccess: createQuestion=> event= " + event + " question= " + question + " betMinimum="
				+ betMinimum);
		
		if (event == null || question == null) return null;

		Event ev = db.find(Event.class, event.getEventNumber());

		if (ev.DoesQuestionExists(question))
			throw new QuestionAlreadyExist(ResourceBundle.getBundle("Etiquetas").getString("ErrorQueryAlreadyExist"));

		db.getTransaction().begin();
		Question q = ev.addQuestion(question, betMinimum);
		// db.persist(q);
		db.persist(ev); // db.persist(q) not required when CascadeType.PERSIST is added in questions
		// property of Event class
		// @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
		db.getTransaction().commit();
		return q;

	}

	public ArrayList<Question> getAllQuestions() {
		System.out.println(">> DataAccess: getAllQuestions");
		ArrayList<Question> res = new ArrayList<Question>();
		TypedQuery<Question> query = db.createQuery("SELECT qu FROM Question qu", Question.class);
		List<Question> questions = query.getResultList();
		for (Question qu : questions) {
			System.out.println(qu.toString());
			res.add(qu);
		}
		return res;
	}

	/**
	 * This method retrieves from the database the events of a given date
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	public ArrayList<Event> getEvents(Date date) {
		System.out.println(">> DataAccess: getEvents");
		ArrayList<Event> res = new ArrayList<Event>();
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1", Event.class);
		query.setParameter(1, date);
		List<Event> events = query.getResultList();
		for (Event ev : events) {
			System.out.println(ev.toString());
			res.add(ev);
		}
		return res;
	}

	public ArrayList<Event> getAllEvents() {
		System.out.println(">> DataAccess: getAllEvents");
		ArrayList<Event> res = new ArrayList<Event>();
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev", Event.class);
		List<Event> events = query.getResultList();
		for (Event ev : events) {
			System.out.println(ev.toString());
			res.add(ev);
		}
		return res;
	}

	/**
	 * This method retrieves from the database the dates a month for which there are
	 * events
	 * 
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	public ArrayList<Date> getEventsMonth(Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		ArrayList<Date> res = new ArrayList<Date>();

		Date firstDayMonthDate = UtilDate.firstDayMonth(date);
		Date lastDayMonthDate = UtilDate.lastDayMonth(date);

		TypedQuery<Date> query = db.createQuery(
				"SELECT DISTINCT ev.eventDate FROM Event ev WHERE ev.eventDate BETWEEN ?1 and ?2", Date.class);
		query.setParameter(1, firstDayMonthDate);
		query.setParameter(2, lastDayMonthDate);
		List<Date> dates = query.getResultList();
		for (Date d : dates) {
			System.out.println(d.toString());
			res.add(d);
		}
		return res;
	}

	public void open(boolean initializeMode) {

		System.out.println("Opening DataAccess instance => isDatabaseLocal: " + c.isDatabaseLocal()
		+ " getDatabBaseOpenMode: " + c.getDataBaseOpenMode());

		String fileName = c.getDbFilename();
		if (initializeMode) {
			fileName = fileName + ";drop";
			System.out.println("Deleting the DataBase");
		}

		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:" + fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			properties.put("javax.persistence.jdbc.user", c.getUser());
			properties.put("javax.persistence.jdbc.password", c.getPassword());

			emf = Persistence.createEntityManagerFactory(
					"objectdb://" + c.getDatabaseNode() + ":" + c.getDatabasePort() + "/" + fileName, properties);

			db = emf.createEntityManager();
		}

	}
	
	public void close() {
		db.close();
		System.out.println("DataBase closed");
	}
	
	public boolean validoUsuario(String puser) throws UserAlreadyExistException {

		User usuarioBD = db.find(User.class, puser);
		if (usuarioBD == null) {
			return true;
		} else {
			throw new UserAlreadyExistException("Ese usuario ya existe");
		}

	}
	
	public RegularUser registrar(String user, String pass, String name, String lastName, String birthDate, String email,
			String account, Integer numb, String address, float balance) throws UserAlreadyExistException {
		db.getTransaction().begin();
		User u = new RegularUser(user, pass, name, lastName, birthDate, email, account, numb, address, balance);

		boolean b = validoUsuario(user);

		if (b) {
			db.persist(u);
			db.getTransaction().commit();
		}

		return (RegularUser)u;
	}
	
	public boolean insertEvent(Event pEvento) {
		try {
			db.getTransaction().begin();
			db.persist(pEvento);
			db.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteEvent(Event evento) {
		try {
			db.getTransaction().begin();

			Event event1 = db.find(Event.class, evento.getEventDate());
			Query query1 = db.createQuery("DELETE FROM Event e WHERE e.getEventNumber()=?1");
			query1.setParameter(1, evento.getEventNumber());

			TypedQuery<Question> query2 = db.createQuery("SELECT qu FROM Question qu", Question.class);
			List<Question> preguntasDB = query2.getResultList();

			for (Question q : preguntasDB) {
				if (q.getEvent() == evento) {
					db.remove(q);
					System.out.println("pregunta eliminada: " + q);
				} else {
					System.out.println("pregunta NO ELIMINADA");
				}
			}

			int events = query1.executeUpdate();
			db.getTransaction().commit();
			System.out.println("Evento eliminado: " + evento);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public Forecast insertForecast(Question q, String forecast, float fee) {
		System.out.println(">> DataAccess: insertForecast=> question= " + q + " forecast= " + forecast + " fee=" + fee);

		Question qe = db.find(Question.class, q.getQuestionNumber());

		if (q.DoesForecastExists(forecast))
			return null;

		db.getTransaction().begin();
		Forecast f = qe.addForecast(forecast, fee);
		db.persist(qe);
		db.getTransaction().commit();
		return f;

	}
	
	public int createBet(User u, Forecast f, Bet b) {
		User us;
		System.out.println(">> DataAccess: crearApuesta=> bet= " + f.getForecast() + " amount=" + b.getAmount() + "user=" + u.getUserName());

		if (b.getAmount() < 0) {
			return 1; // 1 --> El usuario no puede apostar valores negativos, obvio
		} else {

			if (b.getAmount() < b.getForecast().getQuestion().getBetMinimum()) {
				return 2; // 2 -- > El usuario ha de apostar valores que sean almenos la cantidad mínima
				// asociada a la pregunta
			} else {
				try {
				us = db.find(User.class, u.getUserName());
				} catch (Exception e) {
					e.printStackTrace();
					return 5;
				}
				if (b.getAmount() > ((RegularUser)u).getBalance()) {
					return 3; // 3 --> El usuario no cuenta con el suficiente dinero en su cuenta para apostarr

				} else {

					try {
						db.getTransaction().begin();
						Forecast fe = db.find(Forecast.class, f);
						if (fe == null) {
							return 5;
						}
						fe.addBet(f, (RegularUser) u, b.getAmount());
						((RegularUser) us).addBet(b);
						((RegularUser) us).setBalance(((RegularUser) us).getBalance() - b.getAmount());
						db.persist(us);
						db.getTransaction().commit();
						return 4;
					} catch (Exception e) {
						e.printStackTrace();
						return 5;
					}
				}
			}
		}
	}


	public static void main(String[] args) {

		DataAccessDaSilva2 data = new DataAccessDaSilva2();
		RegularUser usuario = new RegularUser("usuario", "Usuario1?", "Nombre", "Apellido", "01/01/2000",
				"usuario@gmail.com", "ES11 1111 1111 1111", 123456789, "", 0);
		AdminUser admin = new AdminUser(adm, adm, adm, adm);
		Event ev1 = new Event(69, "Eibar-Eibar", UtilDate.newDate(2025, 4, 17));
		Question pregunta = new Question("pregunta", 2, ev1);
		Forecast pronostico = new Forecast("Madrid", 17, pregunta);
		Bet apuesta = new Bet(pronostico, usuario, 13);

	}

}