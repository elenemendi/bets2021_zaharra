package domain;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class RegisteredUser extends User implements Serializable {

	private double money;
	private String userType;
	private String cCard;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)//, orphanRemoval = true)
	private Vector<Bet> bets;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Vector<Movement> movements;
	
	private double wonMoney;


	public RegisteredUser() {
		super();
		
	}

	public RegisteredUser(String name, String surname, String idCard, String email, String username, String password,
			Date birthDate, String userType, String cCard) {
		super(name, surname, idCard, email, username, password, birthDate);
		this.money = 0.0;
		this.userType = userType;
		this.cCard = cCard;
		this.bets = new Vector<Bet>();
		this.movements = new Vector<Movement>();

	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getcCard() {
		return cCard;
	}

	public void setcCard(String cCard) {
		this.cCard = cCard;
	}

	public Vector<Bet> getBets() {
		return this.bets;
	}

	public void setBets(Vector<Bet> bets) {
		this.bets = bets;
	}

	public Bet addBet(double betMoney, Date date, Fee f, String description, String betUserUsername) {
		Bet b = new Bet(betMoney, this, date, f, description, betUserUsername);
		this.bets.add(b);
		return b;
	}
	
	public void addBet (Bet bet) {
		this.bets.add(bet);
		
	}

	public void removeBet(Bet b) {
		this.bets.remove(b);

	}

	@Override
	public String toString() {
		return "Registered user: " + this.getUsername();

	}

	public boolean doesBetExist(int betNumber) {
		if (!this.bets.isEmpty()) {
			for (Bet b : this.bets) {
				if (b.getBetNumber() == betNumber)
					return true;
			}

		}

		return false;
	}


	public void addMovement(double income, double quantity, String description) {
		Movement mov = new Movement(income, quantity, description, this);
		this.movements.add(mov);

	}

	public Vector<Movement> getMovements() {
		return movements;
	}

	public void setMovements(Vector<domain.Movement> movements) {
		this.movements = movements;
	}
	public double getWonMoney() {
		return wonMoney;
	}

	public void setWonMoney(double wonMoney) {
		this.wonMoney = wonMoney;
	}



}
