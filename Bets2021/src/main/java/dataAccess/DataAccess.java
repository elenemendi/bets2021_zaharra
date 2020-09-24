package dataAccess;

import java.util.Calendar;
import java.util.Collections;
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

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.Admin;
import domain.Bet;
import domain.Event;
import domain.Fee;
import domain.FeeContainer;
import domain.Movement;
import domain.Question;
import domain.RegisteredUser;
import domain.User;
import exceptions.ExistingBet;
import exceptions.ExistingFee;
import exceptions.InsufficientMoney;
import exceptions.QuestionAlreadyExist;
import java.util.Comparator;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess {
	protected static EntityManager db;
	protected static EntityManagerFactory emf;

	ConfigXML c;

	public DataAccess(boolean initializeMode) {

		c = ConfigXML.getInstance();

		System.out.println("Creating DataAccess instance => isDatabaseLocal: " + c.isDatabaseLocal()
				+ " getDatabBaseOpenMode: " + c.getDataBaseOpenMode());

		String fileName = c.getDbFilename();
		if (initializeMode)
			fileName = fileName + ";drop";

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
		try {

			Calendar today = Calendar.getInstance();

			int month = today.get(Calendar.MONTH);
			month += 1;
			int year = today.get(Calendar.YEAR);
			if (month == 12) {
				month = 0;
				year += 1;
			}

			Admin proba = new Admin("Inigo", "Zaldua", "722553302B", "x@gmail.com", "admin1", "si1234", null);
			Admin proba2 = new Admin("Inigo", "Zaldua", "722553302B", "x@gmail.com", "admin2", "si1234", null);
			RegisteredUser user1 = new RegisteredUser("Inigo", "Gabirondo", "722553302B", "a@b.com", "user1", "si1234",
					null, "registered", "sdfdsf");
			RegisteredUser user2 = new RegisteredUser("Inigo", "Gabirondo", "722553302B", "a@b.com", "user2", "si1234",
					null, "registered", "sdfdsf");
			RegisteredUser user3 = new RegisteredUser("Inigo", "Gabirondo", "722553302B", "a@b.com", "user3", "si1234",
					null, "registered", "sdfdsf");
			RegisteredUser user4 = new RegisteredUser("Inigo", "Gabirondo", "722553302B", "a@b.com", "user4", "si1234",
					null, "registered", "sdfdsf");

			user1.setMoney(60.0);
			user2.setMoney(50.0);
			user1.setWonMoney(50.0);
			user2.setWonMoney(70.0);
			user3.setWonMoney(50.0);
			user4.setWonMoney(35.65);
			

			Event ev1 = new Event(1, "Atlético-Athletic", UtilDate.newDate(year, month, 17));
			Event ev2 = new Event(2, "Eibar-Barcelona", UtilDate.newDate(year, month, 17));
			Event ev3 = new Event(3, "Getafe-Celta", UtilDate.newDate(year, month, 17));
			Event ev4 = new Event(4, "Alavés-Deportivo", UtilDate.newDate(year, month, 17));
			Event ev5 = new Event(5, "Español-Villareal", UtilDate.newDate(year, month, 17));
			Event ev6 = new Event(6, "Las Palmas-Sevilla", UtilDate.newDate(year, month, 17));
			Event ev7 = new Event(7, "Malaga-Valencia", UtilDate.newDate(year, month, 17));
			Event ev8 = new Event(8, "Girona-Leganés", UtilDate.newDate(year, month, 17));
			Event ev9 = new Event(9, "Real Sociedad-Levante", UtilDate.newDate(year, month, 17));
			Event ev10 = new Event(10, "Betis-Real Madrid", UtilDate.newDate(year, month, 17));

			Event ev11 = new Event(11, "Atletico-Athletic", UtilDate.newDate(year, month, 1));
			Event ev12 = new Event(12, "Eibar-Barcelona", UtilDate.newDate(year, month, 1));
			Event ev13 = new Event(13, "Getafe-Celta", UtilDate.newDate(year, month, 1));
			Event ev14 = new Event(14, "Alavés-Deportivo", UtilDate.newDate(year, month, 1));
			Event ev15 = new Event(15, "Español-Villareal", UtilDate.newDate(year, month, 1));
			Event ev16 = new Event(16, "Las Palmas-Sevilla", UtilDate.newDate(year, month, 1));

			Event ev17 = new Event(17, "Málaga-Valencia", UtilDate.newDate(year, month, 28));
			Event ev18 = new Event(18, "Girona-Leganés", UtilDate.newDate(year, month, 28));
			Event ev19 = new Event(19, "Real Sociedad-Levante", UtilDate.newDate(year, month, 28));
			Event ev20 = new Event(20, "Betis-Real Madrid", UtilDate.newDate(year, month, 28));
			Event ev21 = new Event(20, "Betis-Real Madrid", UtilDate.newDate(year, 2, 29));
		
			Question q1;
			Question q2;
			Question q3;
			Question q4;
			Question q5;
			Question q6;
			Question q7;
		

			if (Locale.getDefault().equals(new Locale("es"))) {
				q1 = ev1.addQuestion("¿Quién ganará el partido?", 1);
				q2 = ev1.addQuestion("¿Quién meterá el primer gol?", 2);
				q3 = ev11.addQuestion("¿Quién ganará el partido?", 1);
				q4 = ev11.addQuestion("¿Cuántos goles se marcarán?", 2);
				q5 = ev17.addQuestion("¿Quién ganará el partido?", 1);
				q6 = ev17.addQuestion("¿Habrá goles en la primera parte?", 2);
				q7 = ev21.addQuestion("¿Habrá goles en la primera parte?", 2);
	
			} else if (Locale.getDefault().equals(new Locale("en"))) {
				q1 = ev1.addQuestion("Who will win the match?", 1);
				q2 = ev1.addQuestion("Who will score first?", 2);
				q3 = ev11.addQuestion("Who will win the match?", 1);
				q4 = ev11.addQuestion("How many goals will be scored in the match?", 2);
				q5 = ev17.addQuestion("Who will win the match?", 1);
				q6 = ev17.addQuestion("Will there be goals in the first half?", 2);
				q7 = ev21.addQuestion("Will there be goals in the first half?", 2);
	
			} else {
				q1 = ev1.addQuestion("Zeinek irabaziko du partidua?", 1);
				q2 = ev1.addQuestion("Zeinek sartuko du lehenengo gola?", 2);
				q3 = ev11.addQuestion("Zeinek irabaziko du partidua?", 1);
				q4 = ev11.addQuestion("Zenbat gol sartuko dira?", 2);
				q5 = ev17.addQuestion("Zeinek irabaziko du partidua?", 1);
				q6 = ev17.addQuestion("Golak sartuko dira lehenengo zatian?", 2);
				q7 = ev21.addQuestion("Golak sartuko dira lehenengo zatian?", 2);
		

			}

			Fee f1 = new Fee(2.00, "3");
			q4.addFee(f1);
			f1.setQuestion(q4);
			
			Fee f2 = new Fee(2.00, "Yes");
			q7.addFee(f2);
			f2.setQuestion(q7);
			


			db.getTransaction().begin();
			db.persist(ev1);
			db.persist(ev2);
			db.persist(ev3);
			db.persist(ev4);
			db.persist(ev5);
			db.persist(ev6);
			db.persist(ev7);
			db.persist(ev8);
			db.persist(ev9);
			db.persist(ev10);
			db.persist(ev11);
			db.persist(ev12);
			db.persist(ev13);
			db.persist(ev14);
			db.persist(ev15);
			db.persist(ev16);
			db.persist(ev17);
			db.persist(ev18);
			db.persist(ev19);
			db.persist(ev20);
			db.persist(ev21);


			db.persist(proba2);
			db.persist(proba);
			db.persist(user1);
			db.persist(user2);
			db.persist(user3);
			db.persist(user4);


			db.getTransaction().commit();
			System.out.println("Db initialized");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Removes an event from the database
	 * 
	 * @param event Event to be removed
	 */
	public void removeEventByNumber(domain.Event event) {
		db.getTransaction().begin();
		int number = event.getEventNumber();
		Event ev = db.find(Event.class, number);
		Vector<Bet> madeBets = ev.getMadeBets();
		
		for (Bet b: madeBets) {
			RegisteredUser user = (RegisteredUser) this.getUserByUsername(b.getBetUserUsername());
			user.removeBet(b);
			
			double betMoney = b.getBetMoney();
			double userMoney = user.getMoney();
			user.setMoney(userMoney + betMoney);
			
			db.persist(user);
			
		}
		
		db.remove(ev);
		db.getTransaction().commit();

	}



	/**
	 * Inserts an event into the database
	 * 
	 * @param event The event to be inserted into the database
	 */
	public void insertEvent(Event event) {
		db.getTransaction().begin();
		db.persist(event);
		db.getTransaction().commit();

	}

	/**
	 * Inserts a normal user into the database
	 * 
	 * @param user User to be registered
	 */
	public void insertRegisteredUser(RegisteredUser user) {
		db.getTransaction().begin();
		db.persist(user);
		db.getTransaction().commit();

	}

	/**
	 * Returns a user found by the username
	 * 
	 * @param username Username of the user
	 * @return null If there is no user with that username
	 * @return registeredUser which has the respective username
	 */
	public User getUserByUsername(String username) {
		TypedQuery<User> query = db.createQuery("SELECT ru FROM User ru WHERE ru.username=?1", User.class);
		query.setParameter(1, username);
		List<User> list = query.getResultList();
		return (list.size() > 0 ? list.get(0) : null);
	}
	
	/**
	 * @return a list with all the users on the database
	 */
	public List<RegisteredUser> getAllRegisteredUsers() {
		TypedQuery<RegisteredUser> query = db.createQuery("SELECT ru FROM RegisteredUser ru", RegisteredUser.class);
		List<RegisteredUser> allUsers = query.getResultList();
		return allUsers;
		
	}
	
	/**
	 * This method returns the 3 users that have won most money
	 * @return a vector with the users that have won most
	 */
	public Vector<RegisteredUser> getTopUsers(){
	 List<RegisteredUser> allUsers = getAllRegisteredUsers();
	 Collections.sort(allUsers, new Comparator<RegisteredUser>(){
		 public int compare(RegisteredUser u1, RegisteredUser u2) {
				if(u1.getWonMoney()<u2.getWonMoney()) {
					return 1;
				}else if (u1.getWonMoney()>u2.getWonMoney()) {
					return -1;
				}else {
					return 0;
				} 
		 }
	 });
		
	 Vector<RegisteredUser> top3 = new Vector<RegisteredUser>();
	
	 for(int i=0; i<=2; i++) {
		 top3.addElement(allUsers.get(i));
		 System.out.println(top3.get(i));
	 }
	 return top3;
	}

	/**
	 * Method to get a Fee by its id
	 * @param i Id of the Fee
	 * @return Fee with the id
	 */
	public Fee getFeeByNumber(int i) {
		TypedQuery<Fee> query = db.createQuery("SELECT ru FROM Fee ru WHERE ru.feeNum=?1", Fee.class);
		query.setParameter(1, i);
		List<Fee> list = query.getResultList();
		return (list.size() > 0 ? list.get(0) : null);
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

	/**
	 * Method used to get the biggest event number
	 * 
	 * @return The event number of the last event
	 */
	public Integer getLastEventNumber() {
		Query query = db.createQuery("SELECT MAX (ev.eventNumber) FROM Event ev");
		Integer lastEventNumber = (Integer) query.getSingleResult();
		return lastEventNumber;

	}

	/**
	 * This method retrieves from the database the events of a given date
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	public Vector<Event> getEvents(Date date) {
		System.out.println(">> DataAccess: getEvents");
		Vector<Event> res = new Vector<Event>();
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1", Event.class);
		query.setParameter(1, date);
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
	public Vector<Date> getEventsMonth(Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		Vector<Date> res = new Vector<Date>();

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

	/**
	 * Closes the database
	 */
	public void close() {
		db.close();
		System.out.println("DataBase closed");
	}

	/**
	 * It creates a Fee and stores it in the database
	 * 
	 * @param question Question to which the Fee belongs
	 * @param pred     Possible answer to the question
	 * @param fact     Factor to multiply to multiply bet money in case of win
	 * @return Created Fee
	 * @throws ExistingFee If the created fee already exists
	 */
	public Fee createFee(Integer id, Question question, String pred, double fact) throws ExistingFee {
		Fee f1 = new Fee(id, fact, pred, question);
		Question q = db.find(Question.class, question.getQuestionNumber());
		// f1.setQuestion(q);
		System.out.println(f1.getFactor());
		if (q.DoesFeeExists(pred))
			throw new ExistingFee(ResourceBundle.getBundle("Etiquetas").getString("ErrorFeeAlreadyExist"));

		db.getTransaction().begin();
		Fee f = q.addFee(f1);
		// db.persist(f);
		db.persist(q); // db.persist(f) not required when CascadeType.PERSIST is added
						// @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
		System.out.println("Fee inserted succesfully: " + f);
		db.getTransaction().commit();
		return f;
	}

	/**
	 * Creates a bet, stores it on the database, and bet money is taken from the
	 * total quantity of the user
	 * 
	 * @param betMoney    Money that the user has bet
	 * @param date        Date of the chosen event
	 * @param u           User that has made the bet
	 * @param f           Chosen Fee
	 * @param description Short description of the bet
	 * @return Created Bet
	 * @throws ExistingBet If the created bet already exists
	 */
	public Bet createBet( float betMoney, Date date, RegisteredUser u, Fee f, String description)
			throws ExistingBet {
		
		RegisteredUser user = (RegisteredUser) this.getUserByUsername(u.getUsername());

		Fee fee = this.getFeeByNumber(f.getFeeNum());
		
		Bet bet = new Bet(betMoney, user, date, fee, description, user.getUsername());

		
		if (betMoney < 0)
			throw new ExistingBet();
	
		double userMoney = u.getMoney();
		
		int questionNumber = f.getQuestion().getQuestionNumber();	//Get question of the fee
		Question q = db.find(Question.class, questionNumber);
		
		int eventNumber = q.getEvent().getEventNumber();
		Event ev = db.find(Event.class, eventNumber);
		
		double moneyWon = q.getWonMoney();
		
		db.getTransaction().begin();
		
		user.addBet(bet);
		q.setWonMoney(moneyWon + betMoney);			//Sum bet money to calculate profits
		fee.addBet(bet);
		user.setMoney(userMoney - betMoney);
		
		ev.addBet(bet);
		
		db.persist(user);
		db.persist(fee);
		db.persist(q);
		db.persist(ev);
		db.getTransaction().commit();

		
		return bet;
	}

	/**
	 * Removes from the database the chosen bet, and returns the bet money to the
	 * user
	 * 
	 * @param user RegisteredUser that made the bet
	 * @param bet  Bet to be removed
	 * @return Removed bet
	 */
	public Bet removeBet(RegisteredUser user, Bet bet, Fee fee, Question question) {
		RegisteredUser u = (RegisteredUser) this.getUserByUsername(user.getUsername());
		Bet b = db.find(Bet.class, bet.getBetNumber());
		Fee f = db.find(Fee.class, fee.getFeeNum());
		Question q = db.find(Question.class, question.getQuestionNumber());
		Event ev = db.find(Event.class, q.getEvent().getEventNumber());

		double money = user.getMoney();
		double betMoney = bet.getBetMoney();
		db.getTransaction().begin();
		u.getBets().remove(b);
		u.setMoney(money + betMoney);
		
		ev.removeBet(b);
		
		f.removeBet(b);
		
		double wonMoney = q.getWonMoney();
		q.setWonMoney(wonMoney - betMoney);
		
		db.persist(f);
		db.persist(q);
		db.persist(u);
		db.persist(ev);
		db.getTransaction().commit();

		return bet;
	}

	/**
	 * Updated the money amount of a RegisteredUser in the datebase
	 * @param user User whose money will be updated
	 * @param quantity The new money quantity the RegisteredUser will have
	 */
	public void updateMoney(RegisteredUser user, double quantity) {
		RegisteredUser u = (RegisteredUser) this.getUserByUsername(user.getUsername());
		db.getTransaction().begin();
		u.setMoney(quantity);
		db.persist(u);
		db.getTransaction().commit();

	}

	/**
	 * Stores a movement on a RegisteredUser on the database
	 * @param mov Movement to be stored
	 * @param u RegisteredUser where the movement will be stored
	 */
	public void createMovement(Movement mov, RegisteredUser u) {
		RegisteredUser u1 = (RegisteredUser) this.getUserByUsername(u.getUsername());
		double income = mov.getIncome();
		double quantity = mov.getQuantity();
		String description = mov.getDescription();
		db.getTransaction().begin();
		u1.addMovement(income, quantity, description);
		db.persist(u1);
		db.getTransaction().commit();

	}

	/**
	 * Stores a winner Fee result for a Question on the database
	 * @param f Fee to be stored as a winner
	 * @param s Question where it will be stored
	 */
	public void enterResult(Fee f, Question s) {
		Question q = db.find(Question.class, s.getQuestionNumber());
		Fee fee = this.getFeeByNumber(f.getFeeNum());
		s.setResult(f);
		q.setResult(fee);
		db.getTransaction().begin();
		db.persist(q);
		db.getTransaction().commit();

	}

	/**
	 * This method retrieves from the database the Questions a Event has
	 * @param event Event that has the Questions
	 * @return Vector with the Questions the Event has
	 */
	public Vector<Question> getQuestionOfEvent(Event event) {

		Vector<Question> res = new Vector<Question>();
		TypedQuery<Question> query = db.createQuery("SELECT q FROM Question q WHERE q.Event=?1", Question.class);
		query.setParameter(1, event);
		List<Question> questions = query.getResultList();
		for (Question q : questions) {
			System.out.println(q.toString());
			res.add(q);
		}
		return res;
	}

	
	/**
	 * This method subtracts a quantity to the profits of the chosen question
	 * @param money Money to subtract
	 * @param question Question to subtract the money
	 * @return Total profit made by the selected question
	 */
	public double subtractProfitsToQuestion(double money, Question question) {
		Question q = db.find(Question.class, question.getQuestionNumber());
		double wonMoney = q.getWonMoney();
		wonMoney = wonMoney - money;
		
		db.getTransaction().begin();
		q.setWonMoney(wonMoney);
		db.persist(q);
		db.getTransaction().commit();
		
		return wonMoney;
		
	}
	
	
	/**
	 * It removes a bet from a user without giving back the bet money
	 * @param username Username of the user that has made the bet
	 * @param bet Bet to be removed
	 * @return The bet removed
	 */
	public Bet removeBetWithoutMoney(String username, Bet bet, Question question) {
		RegisteredUser user1 = (RegisteredUser) this.getUserByUsername(username);
		Bet bet1 = db.find(Bet.class, bet.getBetNumber());
		Question q = db.find(Question.class, question.getQuestionNumber());
		Event ev = db.find(Event.class, q.getEvent().getEventNumber());
		
		db.getTransaction().begin();
		user1.removeBet(bet1);
		ev.removeBet(bet1);
		db.persist(ev);
		db.persist(user1);
		db.getTransaction().commit();
		
		return bet1;
		
	}

	/**
	 * It updates the won money of an user
	 * @param user RegisteredUser whose won money  needs to be updated
	 * @param quantity Quantity The amount of money to be added 
	 */
	public void updateWonMoney(RegisteredUser user, double quantity) {
		RegisteredUser u = (RegisteredUser) getUserByUsername(user.getUsername());
		db.getTransaction().begin();
		u.setWonMoney(quantity);
		db.persist(u);
		db.getTransaction().commit();
		
	}
	
	
	/**
	 * This method replicates all the bets of another user
	 * @param user User that will take all the bets
	 * @param bets Bets to be replicated
	 * @param betMoney Money that has been bet
	 */
	public void replicateUser(RegisteredUser user, Vector<Bet> bets, double betMoney) {
		RegisteredUser user1 = (RegisteredUser) getUserByUsername(user.getUsername());
		db.getTransaction().begin();
		double userMoney;
		
		for (Bet b: bets) {
			Bet bet = this.getBetById(b.getBetNumber());
			Fee fee = this.getFeeByNumber(bet.getFee().getFeeNum());
			
			userMoney = user1.getMoney();
			double m = b.getBetMoney();
			Date d = b.getDate();
			String desc = b.getDescription();
			Bet bet2 = user1.addBet(m, d, fee, desc, user1.getUsername()); //Update bets, movements and money of the user
			user1.addMovement(m, userMoney - m, "Bet replicated: " + desc);
			user1.setMoney(userMoney - m);
			
			fee.addBet(bet2);
			
			FeeContainer fc = new FeeContainer(fee);
			Question q = fc.getQuestion();	//Update each won money per bet
			Question question = db.find(Question.class, q.getQuestionNumber());
			double wonMoney = question.getWonMoney();
			question.setWonMoney(wonMoney + m);
			
			Event ev = db.find(Event.class, question.getEvent().getEventNumber());
			ev.addBet(bet2);
			
			db.persist(ev);
			db.persist(question);
			db.persist(fee);
			
			System.out.println(question.getQuestion() + " 's won money by the system: " + question.getWonMoney());
		}
		
		db.persist(user1);
		System.out.println(user1.getUsername() + " user's bets: " + user1.getBets());
		db.getTransaction().commit();
		
	}
	
	/**
	 * This method returns a bet with the inserted id
	 * @param id of the bet
	 * @return Bet
	 */
	public Bet getBetById(int id) {
		Bet bet = db.find(Bet.class, id);
		return bet;
	}
	
	
	/**
	 * Method to get a question by its id
	 * @param id Id of the question
	 * @return Question with its id
	 */
	public Question getQuestionById(int id) {
		TypedQuery<Question> query = db.createQuery("SELECT q FROM Question q WHERE q.questionNumber=?1", Question.class);
		query.setParameter(1, id);
		List<Question> list = query.getResultList();
		return (list.size() > 0 ? list.get(0) : null);
		
	}

}
