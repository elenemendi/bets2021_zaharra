package domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Bet implements Serializable {

	@Id
	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer betNumber;

	private double betMoney;
	private double wonMoney;
	private String description;
	private Date date;
	private String betUserUsername;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@XmlIDREF
	private RegisteredUser user;
	@ManyToOne(fetch = FetchType.EAGER)
	@XmlIDREF
	private Fee fee;

	public Bet() {
		super();
		
	}

	public Bet(int betNumber, double betMoney, RegisteredUser u, Date date, Fee f, String description) {
		super();
		this.betNumber = betNumber;
		this.betMoney = betMoney;
		this.wonMoney = betMoney * f.getFactor();
		this.user = u;
		this.date = date;
		this.fee = f;
		this.description = description;

	}
	
	public Bet(double betMoney, RegisteredUser u, Date date, Fee f, String description, String betUserUsername) {
		super();
		this.betMoney = betMoney;
		this.wonMoney = betMoney * f.getFactor();
		this.user = u;
		this.date = date;
		this.fee = f;
		this.description = description;
		this.betUserUsername = betUserUsername;
		
	}
	
	

	public int getBetNumber() {
		return betNumber;
	}

	public void setBetNumber(int betNumber) {
		this.betNumber = betNumber;
	}

	public double getBetMoney() {
		return betMoney;
	}

	public void setBetMoney(double betMoney) {
		this.betMoney = betMoney;
	}

	public double getWonMoney() {
		return wonMoney;
	}

	public void setWonMoney(double wonMoney) {
		this.wonMoney = wonMoney;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date d) {
		this.date = d;
	}

	public RegisteredUser getUser() {
		return user;
	}

	public void setUser(RegisteredUser user) {
		this.user = user;
	}

	public Fee getFee() {
		return fee;
	}

	public void setFee(Fee fee) {
		this.fee = fee;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String d) {
		this.description = d;
	}

	public String getBetUserUsername() {
		return this.betUserUsername;
		
	}
	
	public void setBetUserUsername(String s) {
		this.betUserUsername = s;
		
	}
	
	@Override
	public String toString() {
		return this.user + ": " + this.fee;

	}

}
