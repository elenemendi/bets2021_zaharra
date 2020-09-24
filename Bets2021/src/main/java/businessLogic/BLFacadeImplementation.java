package businessLogic;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jws.WebMethod;
import javax.jws.WebService;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Admin;
import domain.Bet;
import domain.BetContainer;
import domain.Event;
import domain.Fee;
import domain.FeeContainer;
import domain.Movement;
import domain.Question;
import domain.QuestionContainer;
import domain.RegisteredUser;
import domain.User;
import exceptions.EmptyDescription;
import exceptions.EmptyField;
import exceptions.EventFinished;
import exceptions.EventUnfinished;
import exceptions.ExistingBet;
import exceptions.ExistingEvent;
import exceptions.ExistingFee;
import exceptions.ExistingUsername;
import exceptions.IncorrectLogin;
import exceptions.InsufficientMoney;
import exceptions.InvalidDate;
import exceptions.InvalidEmail;
import exceptions.InvalidID;
import exceptions.LessThanTheMinimumMoney;
import exceptions.NotExistingUser;
import exceptions.NotMatchingPasswords;
import exceptions.NotSelectedBet;
import exceptions.NotSelectedFee;
import exceptions.QuestionAlreadyExist;
import exceptions.SameUser;
import exceptions.Underage;
import exceptions.UnspecifiedMovement;
import exceptions.WinnerAlreadyExist;

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation implements BLFacade {

	public BLFacadeImplementation() {
		System.out.println("Creating BLFacadeImplementation instance");
		ConfigXML c = ConfigXML.getInstance();

		if (c.getDataBaseOpenMode().equals("initialize")) {
			DataAccess dbManager = new DataAccess(c.getDataBaseOpenMode().equals("initialize"));
			dbManager.initializeDB();
			dbManager.close();
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
	 * @throws EventFinished        if current data is after data of the event
	 * @throws QuestionAlreadyExist if the same question already exists for the
	 *                              event
	 */
	@WebMethod
	public Question createQuestion(Event event, String question, float betMinimum)
			throws EventFinished, QuestionAlreadyExist {

		// The minimum bed must be greater than 0
		DataAccess dBManager = new DataAccess();
		Question qry = null;

		if (new Date().compareTo(event.getEventDate()) > 0)
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));

		qry = dBManager.createQuestion(event, question, betMinimum);

		dBManager.close();

		return qry;
	};

	/**
	 * This method invokes the data access to retrieve the events of a given date
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod
	public List<Event> getEvents(Date date) {
		DataAccess dbManager = new DataAccess();
		Vector<Event> events = dbManager.getEvents(date);
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
	@WebMethod
	public List<Date> getEventsMonth(Date date) {
		DataAccess dbManager = new DataAccess();
		Vector<Date> dates = dbManager.getEventsMonth(date);
		dbManager.close();
		return dates;
	}

	/**
	 * This method logs in a user.
	 * 
	 * @param username
	 * @param password
	 * @return 0 If the logged in user is an administrator
	 * @return 1 If the logged in user is a normal user
	 * @throws IncorrectLogin if there is no username that fits with the password
	 */
	@WebMethod
	public int login(String username, String password) throws IncorrectLogin {
		DataAccess dbManager = new DataAccess();
		User user = dbManager.getUserByUsername(username);

		if (user == null) {
			throw new IncorrectLogin();

		}

		if (user.getPassword().compareTo(password) == 0) {
			System.out.println("Login correct.");
			dbManager.close();
			if (user instanceof Admin) {
				return 0;

			} else {
				return 1;

			}

		} else {
			System.out.println("Login incorrect.");
			dbManager.close();
			throw new IncorrectLogin();

		}

	}

	/**
	 * This method invokes the data access to initialize the database with some
	 * events and questions. It is invoked only when the option "initialize" is
	 * declared in the tag dataBaseOpenMode of resources/config.xml file
	 */
	@WebMethod
	public void initializeBD() {
		DataAccess dBManager = new DataAccess();
		dBManager.initializeDB();
		dBManager.close();
	}

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	/**
	 * This method says if the given email follows the format a@b.com
	 * 
	 * @param emailStr Given email by the registered user
	 * @return If the email follows the correct format
	 */
	@WebMethod
	public static boolean validate(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.find();
	}

	/**
	 * It returns whether the user is already registered or not.
	 * 
	 * @param username The username of the user
	 */
	@WebMethod
	public boolean isRegistered(String username) {
		DataAccess db = new DataAccess();
		User user = db.getUserByUsername(username);

		if (user == null) {
			return false;

		} else {
			return true;

		}

	}

	/**
	 * It looks if the ID is correct
	 * 
	 * @param ID of the user
	 * @return true if the ID is correct
	 */
	public boolean correctID(String ID) {

		if (ID.length() != 9) {
			return false;
		} else {

			char[] dni = ID.toCharArray();
			char[] letters = new char[] { 'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z',
					'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E' };
			// int[] numbers = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0 };
			char dig_control = dni[8];
			int dni_num = Integer.parseInt(ID.substring(0, 8));
			if (letters[dni_num % 23] == dig_control) {
				return true;
			} else {
				return false;
			}

		}
	}

	/**
	 * This method registers a user and stores it in the database
	 * 
	 * @param name      of the user
	 * @param surname   of the user
	 * @param idNumber  ID card of the user
	 * @param email     of the user
	 * @param username  of the user
	 * @param password  of the user
	 * @param password2 of the user
	 * @param birthDate Birth date of the user
	 * @param userType  Specifies which type of user is
	 * @param cCard     Credit card of the user
	 * @throws ExistingUsername     If the chosen username is already used
	 * @throws NotMatchingPasswords If password1 and password2 are different
	 * @throws Underage             If the user is underage
	 * @throws ParseException       If the introduced date has a wrong format
	 * @throws InvalidID            If the introduced ID card is not correct
	 */
	@WebMethod
	public void registerUser(String name, String surname, String idNumber, String email, String username,
			String password, Date birthDate, String userType, String cCard, String password2)
			throws ExistingUsername, NotMatchingPasswords, Underage, InvalidEmail, ParseException, InvalidID {

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(birthDate);
		int year = calendar.get(Calendar.YEAR);

		if (password.compareTo(password2) != 0) {
			throw new NotMatchingPasswords();

		} else if (isRegistered(username)) {
			throw new ExistingUsername();

		} else if (year > 2002) {
			throw new Underage();

		} else if (!validate(email)) {
			throw new InvalidEmail();

		} else if (!correctID(idNumber)) {
			throw new InvalidID();

		} else {
			RegisteredUser user = new RegisteredUser(name, surname, idNumber, email, username, password, birthDate,
					userType, cCard);

			DataAccess dBManager = new DataAccess();
			dBManager.insertRegisteredUser(user);
			dBManager.close();

		}

	}

	/**
	 * This method creates an event and stores it in the database
	 * 
	 * @param eventNumber The number of the event
	 * @param description Description of the event
	 * @param date        Date in which the event is going to be celebrated
	 * @throws EmptyDescription If the inserted description is empty
	 * @throws InvalidDate      If the introduced date has already passed
	 * @throws ExistingEvent    If the event already exists
	 */
	@WebMethod
	public void createEvent(int eventNumber, String description, Date date)
			throws EmptyDescription, InvalidDate, ExistingEvent {

		if (description.equals("")) {
			throw new EmptyDescription();

		}

		java.util.Date currentDate = new Date();

		if (currentDate.compareTo(date) > 0) {
			throw new InvalidDate();

		}

		List<Event> events = this.getEvents(date);
		for (Event ev : events) {
			if (ev.getDescription().equals(description)) {
				throw new ExistingEvent();

			}

		}

		Event event = new Event(eventNumber, description, date);

		DataAccess dBManager = new DataAccess();
		dBManager.insertEvent(event);
		dBManager.close();

	}

	/**
	 * The method returns the last event's number
	 * 
	 * @return The event number of the last event stored in the database
	 */
	@WebMethod
	public Integer getLastEventNumber() {
		DataAccess dBManager = new DataAccess();
		Integer eventNumber = dBManager.getLastEventNumber();
		dBManager.close();
		return eventNumber;

	}

	/**
	 * The method removes a event from the database
	 * 
	 * @param event The event to be removed
	 */
	@WebMethod
	public void removeEvent(domain.Event event) {
		DataAccess dBManager = new DataAccess();
		dBManager.removeEventByNumber(event);
		dBManager.close();

	}


	/**
	 * It creates a possible to a query
	 * @param id Id of the Fee
	 * @param q Question to which the create fee belongs
	 * @param inputPred Possible answer to the question
	 * @param inputFactor Factor to multiply bet money in case of win
	 * @return created fee
	 * @throws EventFinished If the chosen event has already finished
	 * @throws ExistingFee If the created fee already exists
	 * @throws EmptyDescription If the inserted description is empty
	 */
	@WebMethod
	public Fee createFee(Integer id, Question q, String inputPred, double inputFactor) throws ExistingFee, EventFinished, EmptyDescription {


		DataAccess dBManager = new DataAccess();
		Fee fee = null;

		Question question = dBManager.getQuestionById(q.getQuestionNumber());
		
		if (new Date().compareTo(question.getEvent().getEventDate()) > 0)
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));
		
		if (inputPred==null) throw new EmptyDescription();

		 fee = dBManager.createFee(id, question,inputPred, inputFactor);


		dBManager.close();
		
		return fee;
		
	}
	
	
	/**
	 * RegisteredUser makes a bet, and bet money is taken from the total quantity of the user
	 * @param betMoney Money that has been bet
	 * @param u User that has bet
	 * @param date Date of the chosen event 
	 * @param f Chosen fee
	 * @param description Short description of the bet
	 * @return Created bet
	 * @throws EventFinished If the chosen event has already finished
	 * @throws NotSelectedFee If the user has not chosen a fee
	 * @throws ExistingBet If the created bet already exists
	 * @throws EmptyDescription If the inserted description is empty
	 * @throws InsufficientMoney If the user has not got sufficient money to bet
	 * @throws LessThanTheMinimumMoney If the user tries to bet a quantity which is less than the minimum of the question
	 */
	@WebMethod
	public Bet createBet(Float betMoney, RegisteredUser u, Date date, Fee f, String description, Question q)
			throws EventFinished, ExistingBet, EmptyDescription, InsufficientMoney, LessThanTheMinimumMoney, NotSelectedFee {
		
		if (new Date().compareTo(date) > 0) throw new EventFinished();
		
		if (betMoney == null) throw new EmptyDescription();	
		
		if (betMoney > u.getMoney()) throw new InsufficientMoney();
		
		if (f == null) throw new NotSelectedFee();
		
		if (betMoney < q.getBetMinimum()) throw new LessThanTheMinimumMoney();
		
		System.out.println(betMoney + " " + u + " " + date + " " + f + " " + description + " " + q);
		
		DataAccess dbManager = new DataAccess();
		Bet bet = dbManager.createBet(betMoney, date, u, f, description);
		dbManager.close();
		System.out.println("proba");
		return bet;
	}
	
	/**
	 * It gets a registeredUser by his username
	 * @param username Username of the registeredUser
	 * @return RegisteredUser
	 */
	@WebMethod
	public RegisteredUser getUserByUsername(String username){
		DataAccess dbManager = new DataAccess();
		RegisteredUser u = (RegisteredUser) dbManager.getUserByUsername(username);	
		dbManager.close();
		return u;
		
	}
	
	/**
	 * It removes a already made bet from a RegisteredUser, and returns the bet money to him.
	 * @param user RegisteredUser that made the bet
	 * @param bet Bet to be removed
	 * @return The removed bet
	 */
	@WebMethod
	public Bet removeBet(RegisteredUser user, Bet bet, Fee fee, Question question) throws NotSelectedBet {
		if (bet == null) throw new NotSelectedBet();
		
		DataAccess dbManager = new DataAccess();
		dbManager.removeBet(user, bet, fee, question);
		dbManager.close();
		return bet;
		
	}
	
	/**
	 * It updates the amount of money a user has
	 * @param user RegisteredUser whose money needs to be updated
	 * @param quantity The amount of money to be added or substracted
	 * @param selected Whether the money has to be added or substracted
	 * @throws InsufficientMoney If the user has not got sufficient money to taken
	 * @throws UnspecifiedMovement If the user has not selected whether they want to take or insert money
	 */
	@WebMethod
	public void updateMoney(RegisteredUser user, double quantity, boolean selected) throws InsufficientMoney, UnspecifiedMovement {
		if(quantity<0){
			throw new InsufficientMoney();
		}
		if(!selected) {
			throw new UnspecifiedMovement();
		}
		DataAccess dbManager = new DataAccess();
		dbManager.updateMoney(user, quantity);
		dbManager.close();
		
	}

	/**
	 * Creates a money movement for a registered user and stores it
	 * @param mov Movement to be stored
	 * @param u RegisteredUser the movement is made for
	 */
	@WebMethod
	public void createMovement(Movement mov, RegisteredUser u) {
		DataAccess dbManager = new DataAccess();
		dbManager.createMovement(mov,u);
		dbManager.close();
		
	}

	/**
	 * This method sets a winner for a Fee
	 * @param f Fee whose winner will be set
	 * @param s Question the fee is from
	 * @param date Date the Event happens
	 * @throws WinnerAlreadyExist If the winner for the Fee is already set
	 * @throws EventUnfinished If the Event has not happened
	 * @throws NotSelectedFee It the user has not selected any fee
	 */
	@WebMethod
	public void enterResult(Fee f, Question s,  Date date) throws  WinnerAlreadyExist, EventUnfinished, NotSelectedFee {
		if (new Date().compareTo(date) < 0) {  // < 0 da zuzena
			throw new EventUnfinished();
		}
		
		if (s.getResult()!=null) {
			throw new WinnerAlreadyExist();
		}
		
		if (f == null) {
			throw new NotSelectedFee();
			
		}
		
		DataAccess dbManager = new DataAccess();
		dbManager.enterResult(f, s);
		dbManager.close();
	}
	
	
	/**
	 * This method subtracts a quantity to the profits of the chosen question
	 * @param money Money to subtract
	 * @param question Question to subtract the money
	 * @return Total profit made by the selected question
	 */
	@WebMethod
	public double subtractProfitsToQuestion(double money, Question question) {
		DataAccess dbManager = new DataAccess();
		double wonMoney = dbManager.subtractProfitsToQuestion(money, question);
		dbManager.close();
		
		return wonMoney;
	}
	
	
	/**
	 * When the answer to a question is set, it removes the bets made by users
	 * @param question Question to which bets are going to be removed
	 */
	@WebMethod
	public void removeBetsFromUser(Question question) {
		Vector<Fee> fees = question.getFees();
		DataAccess dbManager = new DataAccess();
		for (Fee f: fees) {
			Vector<Bet> bets = f.getBets();
			if (bets == null) {
				bets = new Vector<Bet>();
				
			}
			
			for (Bet b: bets) {
				String username = b.getBetUserUsername();
				dbManager.removeBetWithoutMoney(username, b, question);
				
			}
			
		}
		
		dbManager.close();
	}

	/**
	 * It updates the won money of an user
	 * @param user RegisteredUser whose won money  needs to be updated
	 * @param quantity Quantity The amount of money to be added 
	 */
	@WebMethod
	public void updateWonMoney(RegisteredUser user, double quantity) {
		DataAccess dbManager = new DataAccess();
		dbManager.updateWonMoney(user, quantity);
		dbManager.close();
		
	}
	
	/**
	 * @return a list with the 3 users that have won the most money
	 */
	@WebMethod
	public List<RegisteredUser> getTopUsers(){
		DataAccess dbManager = new DataAccess();
		Vector<RegisteredUser> res = dbManager.getTopUsers();
		dbManager.close();
		return res;
	}
	
	/**
	 * This method gets the registered user to replicate
	 * @param replicatedUsername Username of the user to replicate
	 * @param username Username of the user that tries to replicate
	 * @return User to be replicated
	 * @throws EmptyField If no username is inserted
	 * @throws NotExistingUser If there is no user with the inserted username
	 * @throws SameUser If the user tries to replicate himself
	 */
	@WebMethod
	public RegisteredUser getUserToReplicate(String replicatedUsername, String username) throws EmptyField, NotExistingUser, SameUser {
		DataAccess dbManager = new DataAccess();
		RegisteredUser u = (RegisteredUser) dbManager.getUserByUsername(replicatedUsername);
		
		if (replicatedUsername.compareTo("") == 0) { 
			throw new EmptyField(); 
		
		}
		
		if (replicatedUsername.compareTo(username) == 0) {
			throw new SameUser();
			
		}
		
		if (u == null) {
			throw new NotExistingUser();
			
		}
		
		dbManager.close();
		return u;
		
	}
	
	/**
	 * This method replicates all the bets of another user
	 * @param user User that will take all the bets
	 * @param bets Bets to be replicated
	 * @param betMoney Money that has been bet
	 * @throws InsufficientMoney if the user has not the neccessary money to make all the bets
	 */
	@WebMethod
	public void replicateUser(RegisteredUser user, Vector<Bet> bets, double betMoney) throws InsufficientMoney {
		double userMoney = user.getMoney();
		
		if (userMoney < betMoney) {
			throw new InsufficientMoney();
			
		}
		
		DataAccess dbManager = new DataAccess();
		dbManager.replicateUser(user, bets, betMoney);
		dbManager.close();
	}
	
	/**
	 * This method returns the betContainer object of a Bet
	 * @param b Bet to return its betContainer
	 * @param user RegisterUser that contains the bet b
	 * @return BetContainer
	 * @throws NotSelectedBet if no bet is chosen
	 */
	@WebMethod 
	public BetContainer getBetContainer(Bet b, RegisteredUser user) throws NotSelectedBet {

		if (b == null) {
			throw new NotSelectedBet();
			
		}
		
		int id = b.getBetNumber();
		DataAccess dbManager = new DataAccess();
		RegisteredUser u = (RegisteredUser) dbManager.getUserByUsername(user.getUsername());
		Vector<Bet> bets = u.getBets();
		System.out.println("User: " + u + " Bets: " + bets);
		for (Bet bet: bets) {
			if (bet.getBetNumber() == id) {
				dbManager.close();
				return new BetContainer(bet);
				
			}
			
		}
		
		return null;
		
	}
	
	
	/**
	 * This method returns the FeeContainer object of a Fee
	 * @param f Fee to return its FeeContainer
	 * @return FeeContainer of the inserted fee
	 */
	@WebMethod 
	public FeeContainer getFeeContainer(Fee f) {
		DataAccess dbManager = new DataAccess();
		Fee fee = dbManager.getFeeByNumber(f.getFeeNum());
		dbManager.close();
		return new FeeContainer(fee);
	}	
	
	
	/**
	 * This method return the QuestionContainer object of a Question
	 * @param q Question to return its QuestionContaienr
	 * @return QuestionContainer of the inserted question
	 */
	@WebMethod 
	public QuestionContainer getQuestionContainer(Question q) {
		DataAccess dbManager = new DataAccess();
		Question question = dbManager.getQuestionById(q.getQuestionNumber());
		dbManager.close();
		return new QuestionContainer(question);
	}

	

}
