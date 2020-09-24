package businessLogic;

import java.util.Vector;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

//import domain.Booking;
import domain.Question;
import domain.QuestionContainer;
import domain.RegisteredUser;
import domain.Bet;
import domain.BetContainer;
import domain.Event;
import domain.Fee;
import domain.FeeContainer;
import domain.Movement;
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

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Interface that specifies the business logic.
 */
/**
 * @author Aiz
 *
 */
@WebService
public interface BLFacade  {


	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 * 
	 * @param event to which question is added
	 * @param question text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws EventFinished if current data is after data of the event
	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
	@WebMethod Question createQuestion(Event event, String question, float betMinimum) throws EventFinished, QuestionAlreadyExist;


	/**
	 * This method retrieves the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod public List<Event> getEvents(Date date);

	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	@WebMethod public List<Date> getEventsMonth(Date date);

	/**
	 * This method calls the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	@WebMethod public void initializeBD();


	/**
	 * This method logs in a user.
	 * @param username
	 * @param password
	 * @return 0 If the logged in user is an administrator
	 * @return 1 If the logged in user is a normal user
	 * @throws IncorrectLogin if there is no username that fits with the password
	 */
	@WebMethod public int login(String username, String password) throws IncorrectLogin;

	/**
	 * This method registers a user and stores it in the database
	 * @param name of the user
	 * @param surname of the user
	 * @param idNumber ID card of the user
	 * @param email of the user
	 * @param username of the user
	 * @param password of the user
	 * @param password2 of the user
	 * @param birthDate Birth date of the user
	 * @param userType Specifies which type of user is
	 * @param cCard Credit card of the user
	 * @throws ExistingUsername If the chosen username is already used
	 * @throws NotMatchingPasswords If password1 and password2 are different
	 * @throws Underage If the user is underage
	 * @throws ParseException If the introduced date has a wrong format
	 * @throws InvalidID If the introduced ID card is not correct 
	 */
	@WebMethod public void registerUser(String name, String surname, String idNumber, String email,
			String username, String password, Date birthDate, String userType, String cCard, String password2)
					throws ExistingUsername,
					NotMatchingPasswords, Underage, InvalidEmail, ParseException, InvalidID;


	/**
	 * It returns whether the user is already registered or not.
	 * @param username The username of the user
	 */
	@WebMethod public boolean isRegistered(String username);

	/**
	 * This method creates an event and stores it in the database
	 * @param eventNumber The number of the event
	 * @param description Description of the event
	 * @param date Date in which the event is going to be celebrated
	 * @throws EmptyDescription If the inserted description is empty
	 * @throws InvalidDate If the introduced date has already passed
	 * @throws ExistingEvent If the event already exists
	 */
	@WebMethod public void createEvent(int eventNumber, String description, Date date) throws EmptyDescription, InvalidDate, ExistingEvent;

	/**
	 * The method returns the last event's number
	 * @return The event number of the last event stored in the database
	 */
	@WebMethod public Integer getLastEventNumber();
	
	

	/**
	 * The method removes a event from the database
	 * @param event The event to be removed
	 */
	@WebMethod public void removeEvent(domain.Event event);


	/**
	 * It creates a possible to a query
	 * @param id Id of the Fee
	 * @param question Question to which the create fee belongs
	 * @param inputPred Possible answer to the question
	 * @param inputFactor Factor to multiply bet money in case of win
	 * @return created fee
	 * @throws EventFinished If the chosen event has already finished
	 * @throws ExistingFee If the created fee already exists
	 * @throws EmptyDescription If the inserted description is empty
	 */
	@WebMethod public Fee createFee (Integer id, Question question, String inputPred, double inputFactor)throws EventFinished, ExistingFee, EmptyDescription ;


	/**
	 * RegisteredUser makes a bet, and bet money is taken from the total quantity of the user
	 * @param betMoney Money that has been bet
	 * @param u User that has bet
	 * @param date Date of the chosen event 
	 * @param f Chosen fee
	 * @param description Short description of the bet
	 * @param que 
	 * @return Created bet
	 * @throws EventFinished If the chosen event has already finished
	 * @throws ExistingBet If the created bet already exists
	 * @throws EmptyDescription If the inserted description is empty
	 * @throws InsufficientMoney If the user has not got sufficient money to bet
	 * @throws LessThanTheMinimumMoney If the user tries to bet a quantity which is less than the minimum of the question
	 */
	@WebMethod public Bet createBet(Float betMoney, RegisteredUser u, Date date, Fee f, String description, Question que)
			throws EventFinished, ExistingBet, EmptyDescription, InsufficientMoney, LessThanTheMinimumMoney, NotSelectedFee;



	/**
	 * It gets a registeredUser by his username
	 * @param username Username of the registeredUser
	 * @return RegisteredUser
	 */
	@WebMethod public RegisteredUser getUserByUsername(String username);

	/**
	 * It removes a already made bet from a RegisteredUser, and returns the bet money to him.
	 * @param user RegisteredUser that made the bet
	 * @param bet Bet to be removed
	 * @return The removed bet
	 */
	@WebMethod public Bet removeBet(RegisteredUser user, Bet bet, Fee fee, Question question) throws NotSelectedBet;

	
	/**
	 * It updates the amount of money a user has
	 * @param user RegisteredUser whose money needs to be updated
	 * @param quantity The amount of money to be added or substracted
	 * @param selected Whether the money has to be added or substracted
	 * @throws InsufficientMoney If the user has not got sufficient money to taken
	 * @throws UnspecifiedMovement If the user has not selected whether they want to take or insert money
	 */
	@WebMethod public void updateMoney(RegisteredUser user, double quantity, boolean selected) throws InsufficientMoney, UnspecifiedMovement;
	
	/**
	 * It updates the won money of an user
	 * @param user RegisteredUser whose won money  needs to be updated
	 * @param quantity Quantity The amount of money to be added 
	 */
	@WebMethod public void updateWonMoney( RegisteredUser user, double quantity);


	/**
	 * Creates a money movement for a registered user and stores it
	 * @param mov Movement to be stored
	 * @param u1 RegisteredUser the movement is made for
	 */
	@WebMethod public void createMovement(Movement mov, RegisteredUser u1);


	/**
	 * This method sets a winner for a Fee
	 * @param selectedFee Fee whose winner will be set
	 * @param q Question the fee is from
	 * @param date Date the Event happens
	 * @throws WinnerAlreadyExist If the winner for the Fee is already set
	 * @throws EventUnfinished If the Event has not happened
	 * @throws NotSelectedFee It the user has not selected any fee
	 */
	@WebMethod public void enterResult(Fee selectedFee, Question q, Date date) throws WinnerAlreadyExist, EventUnfinished, NotSelectedFee;
	
	/**
	 * This method subtracts a quantity to the profits of the chosen question
	 * @param money Money to subtract
	 * @param question Question to subtract the money
	 * @return Total profit made by the selected question
	 */
	@WebMethod public double subtractProfitsToQuestion(double money, Question question);
	
	
	/**
	 * When the answer to a question is set, it removes the bets made by users
	 * @param question Question to which bets are going to be removed
	 */
	@WebMethod public void removeBetsFromUser(Question question);


	/**
	 * @return a list with the 3 users that have won the most money
	 */
	@WebMethod public List<RegisteredUser> getTopUsers();

	
	/**
	 * This method gets the registered user to replicate
	 * @param replicatedUsername Username of the user to replicate
	 * @param username Username of the user that tries to replicate
	 * @return User to be replicated
	 * @throws EmptyField If no username is inserted
	 * @throws NotExistingUser If there is no user with the inserted username
	 * @throws SameUser If the user tries to replicate himself
	 */
	@WebMethod public RegisteredUser getUserToReplicate(String replicatedUsername, String username) throws EmptyField, NotExistingUser, SameUser;
	
	
	/**
	 * This method replicates all the bets of another user
	 * @param user User that will take all the bets
	 * @param bets Bets to be replicated
	 * @param betMoney Money that has been bet
	 * @throws InsufficientMoney if the user has not the neccessary money to make all the bets
	 */
	@WebMethod public void replicateUser(RegisteredUser user, Vector<Bet> bets, double betMoney) throws InsufficientMoney;

	
	/**
	 * This method returns the BetContainer object of a Bet
	 * @param b Bet to return its BetContainer
	 * @param user RegisterUser that contains the bet b
	 * @return BetContainer of the inserted bet
	 * @throws NotSelectedBet if no bet is chosen
	 */
	@WebMethod public BetContainer getBetContainer(Bet b, RegisteredUser user) throws NotSelectedBet;
	
	/**
	 * This method returns the FeeContainer object of a Fee
	 * @param f Fee to return its FeeContainer
	 * @return FeeContainer of the inserted fee
	 */
	@WebMethod public FeeContainer getFeeContainer(Fee f);
	
	
	/**
	 * This method return the QuestionContainer object of a Question
	 * @param q Question to return its QuestionContaienr
	 * @return QuestionContainer of the inserted question
	 */
	@WebMethod public QuestionContainer getQuestionContainer(Question q);
	
	
}
