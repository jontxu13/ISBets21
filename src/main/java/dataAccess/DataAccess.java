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
public class DataAccess {
	protected static EntityManager db;
	protected static EntityManagerFactory emf;
	static String adm = "admin";

	ConfigXML c = ConfigXML.getInstance();

	public DataAccess(boolean initializeMode) {

		System.out.println("Creating DataAccess instance => isDatabaseLocal: " + c.isDatabaseLocal()
		+ " getDatabBaseOpenMode: " + c.getDataBaseOpenMode());

		open(initializeMode);

	}

	public DataAccess() {
		new DataAccess(false);
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

//			Event ev1 = new Event(1, "Atlético-Athletic", UtilDate.newDate(year, month, 17));
//			Event ev2 = new Event(2, "Eibar-Barcelona", UtilDate.newDate(year, month, 17));
//			Event ev3 = new Event(3, "Getafe-Celta", UtilDate.newDate(year, month, 17));
//			Event ev4 = new Event(4, "Alavés-Deportivo", UtilDate.newDate(year, month, 17));
//			Event ev5 = new Event(5, "Español-Villareal", UtilDate.newDate(year, month, 17));
//			Event ev6 = new Event(6, "Las Palmas-Sevilla", UtilDate.newDate(year, month, 17));
//			Event ev7 = new Event(7, "Malaga-Valencia", UtilDate.newDate(year, month, 17));
//			Event ev8 = new Event(8, "Girona-Leganés", UtilDate.newDate(year, month, 17));
//			Event ev9 = new Event(9, "Real Sociedad-Levante", UtilDate.newDate(year, month, 17));
//			Event ev10 = new Event(10, "Betis-Real Madrid", UtilDate.newDate(year, month, 17));
//
//			Event ev11 = new Event(11, "Atletico-Athletic", UtilDate.newDate(year, month, 1));
//			Event ev12 = new Event(12, "Eibar-Barcelona", UtilDate.newDate(year, month, 1));
//			Event ev13 = new Event(13, "Getafe-Celta", UtilDate.newDate(year, month, 1));
//			Event ev14 = new Event(14, "Alavés-Deportivo", UtilDate.newDate(year, month, 1));
//			Event ev15 = new Event(15, "Español-Villareal", UtilDate.newDate(year, month, 1));
//			Event ev16 = new Event(16, "Las Palmas-Sevilla", UtilDate.newDate(year, month, 1));
//
//			Event ev17 = new Event(17, "Málaga-Valencia", UtilDate.newDate(year, month + 1, 28));
//			Event ev18 = new Event(18, "Girona-Leganés", UtilDate.newDate(year, month + 1, 28));
//			Event ev19 = new Event(19, "Real Sociedad-Levante", UtilDate.newDate(year, month + 1, 28));
//			Event ev20 = new Event(20, "Betis-Real Madrid", UtilDate.newDate(year, month + 1, 28));
//
			User useradmin = new AdminUser(adm, adm, "Pepe", "Lopez");
			User usuario = new RegularUser("usuario", "Usuario1?", "Usuario", "Apellido", "01/01/1997", "usuario@gmail.com", "ES11 1111 1111 1111", 123456789, "", 0);
			db.persist(usuario);
			db.persist(useradmin);
//			Question q1;
//			Question q2;
//			Question q3;
//			Question q4;
//			Question q5;
//			Question q6;
//
//			if (Locale.getDefault().equals(new Locale("es"))) {
//				q1 = ev1.addQuestion("¿Quién ganará el partido?", 1);
//				q2 = ev1.addQuestion("¿Quién meterá el primer gol?", 2);
//				q3 = ev11.addQuestion("¿Quién ganará el partido?", 1);
//				q4 = ev11.addQuestion("¿Cuántos goles se marcarán?", 2);
//				q5 = ev17.addQuestion("¿Quién ganará el partido?", 1);
//				q6 = ev17.addQuestion("¿Habrá goles en la primera parte?", 2);
//			} else if (Locale.getDefault().equals(new Locale("en"))) {
//				q1 = ev1.addQuestion("Who will win the match?", 1);
//				q2 = ev1.addQuestion("Who will score first?", 2);
//				q3 = ev11.addQuestion("Who will win the match?", 1);
//				q4 = ev11.addQuestion("How many goals will be scored in the match?", 2);
//				q5 = ev17.addQuestion("Who will win the match?", 1);
//				q6 = ev17.addQuestion("Will there be goals in the first half?", 2);
//			} else {
//				q1 = ev1.addQuestion("Zeinek irabaziko du partidua?", 1);
//				q2 = ev1.addQuestion("Zeinek sartuko du lehenengo gola?", 2);
//				q3 = ev11.addQuestion("Zeinek irabaziko du partidua?", 1);
//				q4 = ev11.addQuestion("Zenbat gol sartuko dira?", 2);
//				q5 = ev17.addQuestion("Zeinek irabaziko du partidua?", 1);
//				q6 = ev17.addQuestion("Golak sartuko dira lehenengo zatian?", 2);
//
//			}
//
//			db.persist(q1);
//			db.persist(q2);
//			db.persist(q3);
//			db.persist(q4);
//			db.persist(q5);
//			db.persist(q6);
//
//			db.persist(ev1);
//			db.persist(ev2);
//			db.persist(ev3);
//			db.persist(ev4);
//			db.persist(ev5);
//			db.persist(ev6);
//			db.persist(ev7);
//			db.persist(ev8);
//			db.persist(ev9);
//			db.persist(ev10);
//			db.persist(ev11);
//			db.persist(ev12);
//			db.persist(ev13);
//			db.persist(ev14);
//			db.persist(ev15);
//			db.persist(ev16);
//			db.persist(ev17);
//			db.persist(ev18);
//			db.persist(ev19);
//			db.persist(ev20);
//
//			db.getTransaction().commit();
//			System.out.println("Db initialized");
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

	public boolean existQuestion(Event event, String question) {
		System.out.println(">> DataAccess: existQuestion=> event= " + event + " question= " + question);
		Event ev = db.find(Event.class, event.getEventNumber());
		return ev.DoesQuestionExists(question);

	}

	public void close() {
		db.close();
		System.out.println("DataBase closed");
	}

	public User login(String username, String pass) throws UserDoesNotExistException, IncorrectPassException {

		User usuario = db.find(User.class, username);

		if (usuario == null) {
			throw new exceptions.UserDoesNotExistException("El usuario no existe");
		}
		if (!pass.equals(usuario.getUserPass())) {
			throw new exceptions.IncorrectPassException("Contraseña incorrecta");
		}
		return usuario;

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
		RegularUser u = new RegularUser(user, pass, name, lastName, birthDate, email, account, numb, address, balance);

		boolean b = validoUsuario(user);

		if (b) {
			db.persist(u);
			db.getTransaction().commit();
		}

		return u;
	}

	public int getNumberEvents() {
		db.getTransaction().begin();
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev ", Event.class);
		return query.getResultList().size();
	}

	public boolean existEvent(Event event) {
		System.out.println(">> DataAccess: existEvent=> event= " + event);

		ArrayList<Event> eventosmismodia = getEvents(event.getEventDate());

		for (Event e : eventosmismodia) {
			if (e.getDescription().equals(event.getDescription())) {
				return true;
			}
		}

		return false;
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

	public int getNumberForecasts() {
		db.getTransaction().begin();
		TypedQuery<Forecast> query = db.createQuery("SELECT f FROM Forecast f ", Forecast.class);
		return query.getResultList().size();
	}

	public ArrayList<Forecast> getForecasts() {
		db.getTransaction().begin();
		ArrayList<Forecast> res = new ArrayList<Forecast>();
		TypedQuery<Forecast> query = db.createQuery("SELECT f FROM Forecast f ", Forecast.class);
		List<Forecast> forecasts = query.getResultList();
		for (Forecast f : forecasts) {
			System.out.println(f.toString());
			res.add(f);
		}
		return res;
	}

	public ArrayList<Forecast> getForecasts(Question pregunta) {
		db.getTransaction().begin();
		ArrayList<Forecast> res = new ArrayList<Forecast>();
		TypedQuery<Forecast> query = db.createQuery("SELECT f FROM Forecast f WHERE f.getQuestion()=?1",
				Forecast.class);
		query.setParameter(1, pregunta);
		List<Forecast> forecasts = query.getResultList();
		for (Forecast f : forecasts) {
			System.out.println(f.toString());
			res.add(f);
		}
		return res;
	}

	public boolean existForecast(Forecast f) {
		System.out.println(">> DataAccess: existForecast=> forecast= " + f);
		return db.find(Forecast.class, f.getForecast()) != null ? true : false;

	}

	public boolean editarPerfilUsuario(String pContraseña, String pUsername, String pNombre, String pApellido,
			String pEmail, String pCuentaBancaria) {
		try {
			db.getTransaction().begin();
			RegularUser usuario = db.find(RegularUser.class, pUsername);
			usuario.setFirstName(pNombre);
			usuario.setLastName(pApellido);
			usuario.setEmail(pEmail);
			usuario.setBankAccount(pCuentaBancaria);
			usuario.setUserPass(pContraseña);
			db.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public boolean editarPerfilUsuarioSinPass(String pUsername, String pNombre, String pApellido, String pEmail,
			String pCuentaBancaria) {
		try {
			db.getTransaction().begin();
			RegularUser usuario = db.find(RegularUser.class, pUsername);
			usuario.setFirstName(pNombre);
			usuario.setLastName(pApellido);
			usuario.setEmail(pEmail);
			usuario.setBankAccount(pCuentaBancaria);
			db.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public ArrayList<User> getAllUsers() {

		ArrayList<User> res = new ArrayList<User>();
		TypedQuery<User> query = db.createQuery("SELECT us FROM User us", User.class);
		List<User> users = query.getResultList();
		for (User us : users) {
			System.out.println(us.toString());
			res.add(us);
		}
		return res;

	}

	public Integer getMaxIdInDB() {

		ArrayList<Event> events = getAllEvents();

		Integer maxid = events.get(0).getEventNumber();

		for (Event e : events) {

			if (e.getEventNumber() > maxid) {
				maxid = e.getEventNumber();
			}
		}

		return maxid;
	}

	public int createBet(User u, Forecast f, Bet b) {

		System.out.println(">> DataAccess: crearApuesta=> bet= " + f.getForecast() + " amount=" + b.getAmount() + "user=" + u.getUserName());

		if (b.getAmount() < 0) {
			return 1; // 1 --> El usuario no puede apostar valores negativos, obvio
		} else {

			if (b.getAmount() < b.getForecast().getQuestion().getBetMinimum()) {
				return 2; // 2 -- > El usuario ha de apostar valores que sean almenos la cantidad mínima
				// asociada a la pregunta
			} else {
				RegularUser us = db.find(RegularUser.class, u.getUserName());

				if (b.getAmount() > us.getBalance()) {
					return 3; // 3 --> El usuario no cuenta con el suficiente dinero en su cuenta para apostarr

				} else {

					try {
						db.getTransaction().begin();
						Forecast fe = db.find(Forecast.class, f);
						fe.addBet(f, (RegularUser) u, b.getAmount());
						us.addBet(b);
						us.setBalance(us.getBalance() - b.getAmount());
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

	public boolean doLogin(String username, String pass) {

		TypedQuery<RegularUser> query1 = db.createQuery("SELECT ru FROM RegularUser ru", RegularUser.class);
		List<RegularUser> regularusers = query1.getResultList();

		for (RegularUser ru : regularusers) {
			if (ru.getUserName().equals(username) && ru.getUserPass().equals(pass)) {
				return true;
			}
		}

		TypedQuery<AdminUser> query2 = db.createQuery("SELECT au FROM AdminUser au", AdminUser.class);
		List<AdminUser> adminusers = query2.getResultList();

		for (AdminUser au : adminusers) {
			if (au.getUserName().equals(username) && au.getUserPass().equals(pass)) {
				return true;
			}
		}

		return false;

	}

	public boolean isAdmin(String pusername, String ppassword) {
		TypedQuery<User> query = db
				.createQuery("SELECT us FROM User us WHERE us.getUserName()=?1 and us.getUserPass()=?2", User.class);
		query.setParameter(1, pusername);
		query.setParameter(2, ppassword);
		List<User> usuarios = query.getResultList();

		for (User u : usuarios) {
			return (u instanceof AdminUser);
		}

		if (usuarios instanceof AdminUser) {
			return true;
		} else {
			return false;
		}
	}

	public RegularUser getRegularUserByUsername(String pusername) {
		System.out.println(">> DataAccess: getRegularUserByUsername");

		TypedQuery<RegularUser> query = db.createQuery("SELECT ru FROM RegularUser ru", RegularUser.class);
		List<RegularUser> regularusers = query.getResultList();

		// ArrayList<Cliente> result = new ArrayList<Cliente>();
		for (RegularUser ru : regularusers) {
			if (ru.getUserName().equals(pusername)) {
				return ru;
			}

		}
		return null;

	}

	public AdminUser getAdminUserByUsername(String pusername) {
		System.out.println(">> DataAccess: getAdminUserByUsername");

		TypedQuery<AdminUser> query = db.createQuery("SELECT au FROM AdminUser au", AdminUser.class);
		List<AdminUser> adminusers = query.getResultList();

		// ArrayList<Admin> result = new ArrayList<Admin>();
		for (AdminUser au : adminusers) {
			if (au.getUserName().equals(pusername)) {
				return au;
			}

		}
		return null;

	}

	public boolean closeEvent(Event e, Question q, Forecast f) {
		try {
			db.getTransaction().begin();	
			Event ev = db.find(Event.class, e);
			Question qe = db.find(Question.class, q);
			Forecast fe = db.find(Forecast.class, f);
			qe.setResult(f.getForecast());
			System.out.println(">> DataAccess: closeEvent=> Event:" + ev.getDescription() + " in question: " + qe.getQuestion() + " with result: " + qe.getResult());
			if(ev.getClosed()) {
				return false;
			}else{
				if(qe.getClosed()) {
					return false;
				}else{
					Vector<Question> qu = new Vector<Question>(e.getQuestions());
					boolean[] questions = new boolean[qu.size()];
					int i = 0;
					for (Question qo : qu) {
						questions[i] = qo.getClosed();
						i++;
					}
					qe.setClosed(true);
					fe.setClosed(true);
					for(boolean bo : questions) {
						if(!bo) {
							ev.setClosed(false);
						}else {
							ev.setClosed(true);
						}
					}
				}
				Vector<Bet> bets = new Vector<Bet>(fe.getBets());
				for (Bet be : bets) {
					Bet bet = db.find(Bet.class, be);
					if(bet.getForecast()==fe) {	
						payUsers(be);
					}else {
						be.setEstadoApuesta("Lose");
					}
				}
			}
			db.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

	}

	private boolean payUsers(Bet b) {

		try {
			Vector<User> usersToPay = new Vector<User>(getAllUsers());
			for (User au : usersToPay) {
				if (au instanceof RegularUser) {
					RegularUser u = (RegularUser) au;
					RegularUser us = db.find(RegularUser.class, u);
					Vector<Bet> userBets = new Vector<Bet>(u.getBets());
					for (Bet be : userBets) {
						Bet bett = db.find(Bet.class, be);
						if (bett.getUser().equals(us)) {
							float sumo = us.getBalance() + (be.getAmount() * b.getForecast().getFee());
							us.setBalance(us.getBalance() + (be.getAmount() * b.getForecast().getFee()));
							bett.setEstadoApuesta("Win");
							System.out.println("le sumo: " + sumo);
							System.out.println("Nuevo saldo: " + us.getBalance());
						}
					}
				}

			}
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public boolean anularApuesta(Bet pApuesta) {

		try {
			db.getTransaction().begin();
			Bet apuesta = db.find(Bet.class, pApuesta);
			RegularUser cliente = db.find(RegularUser.class, pApuesta.getUser());

			Calendar fecha = new java.util.GregorianCalendar();
			int año = fecha.get(Calendar.YEAR);
			int mes = fecha.get(Calendar.MONTH);
			int dia = fecha.get(Calendar.DAY_OF_MONTH);

			if (apuesta.getForecast().getQuestion().getEvent().getEventDate()
					.compareTo(UtilDate.newDate(año, mes, dia)) > 0) { // posterior al argumento (actual)

				apuesta.setEstadoApuesta("Canceled");
				System.out.println("Saldo inicial: " + cliente.getBalance());
				cliente.setBalance(cliente.getBalance() + pApuesta.getAmount());
				System.out.println("Se ha devuelto " + pApuesta.getAmount());
				System.out.println("Saldo actualizado: " + cliente.getBalance());

				db.getTransaction().commit();

				System.out.println("Canceled");
				return true;
			} else {
				System.out.println("Not canceled");
				return false;
			}
		} catch (Exception e) {
			return false;
		}

	}
	
	public boolean cancelEvent(Event e) {
		try {
			db.getTransaction().begin();
			Event ev = db.find(Event.class, e);
			
			if (ev.getClosed()) {
				return false;
			}else {
				ev.setClosed(true);
				System.out.println("Canceled event:" + ev.toString());
				
				//TODO Devolver dinero a los que apostaron en el evento y marcar apuesta como cancelada (implementar)
				
				db.getTransaction().commit();
				return true;
			}
			
		} catch (Exception ex) {
			return false;
		}
	}
	
	public boolean addMoney(RegularUser u, float n) {
		try {
			db.getTransaction().begin();
			RegularUser us = db.find(RegularUser.class, u);	
			System.out.println("Old money: " + us.getBalance() + "€");
			us.setBalance(us.getBalance() + n);
			System.out.println( n + "€ charged to: " + us.getFirstName() + " " + us.getLastName() + " now you have: " + us.getBalance() + "€");
			db.getTransaction().commit();
			return true;
		}catch (Exception ex) {
			return false;
		}
	}

	public static void main(String[] args) {

		DataAccess data = new DataAccess();
		RegularUser usuario = new RegularUser("usuario", "Usuario1?", "Nombre", "Apellido", "01/01/2000",
				"usuario@gmail.com", "ES11 1111 1111 1111", 123456789, "", 0);
		AdminUser admin = new AdminUser(adm, adm, adm, adm);
		Event ev1 = new Event(69, "Eibar-Eibar", UtilDate.newDate(2025, 4, 17));
		Question pregunta = new Question("pregunta", 2, ev1);
		Forecast pronostico = new Forecast("Madrid", 17, pregunta);
		Bet apuesta = new Bet(pronostico, usuario, 13);

	}

}