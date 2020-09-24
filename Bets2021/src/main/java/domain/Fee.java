package domain;

import java.io.Serializable;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Fee implements Serializable {

	@Id
	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer feeNum;

	private double factor;
	private String prediction;

	@XmlIDREF
	@ManyToOne(fetch = FetchType.EAGER)
	private Question question;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Vector<Bet> bets;

	public Fee() {
		super();

	}
	
	public Fee(Integer feeNum, double factor, String prediction) {
		this.factor = factor;
		this.prediction = prediction;
		this.bets = new Vector<Bet>();

	}

	public Fee(double factor, String prediction) {
		this.factor = factor;
		this.prediction = prediction;
		this.bets = new Vector<Bet>();

	}



	public Fee(Integer feeNum, double factor, String prediction, Question question) {
		super();
		this.feeNum = feeNum;
		this.prediction = prediction;
		this.factor = factor;
		this.question = question;
		this.bets = new Vector<Bet>();
	}

	public double getFactor() {
		return factor;
	}

	public void setFactor(double factor) {
		this.factor = factor;
	}

	public String getPrediction() {
		return prediction;
	}

	public void setPrediction(String prediction) {
		this.prediction = prediction;
	}

	public Integer getFeeNum() {
		return feeNum;
	}

	public void setFeeNum(Integer feeNum) {
		this.feeNum = feeNum;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Vector<Bet> getBets() {
		return this.bets;
	}

	public void setBets(Vector<Bet> b) {
		this.bets = b;
	}

	public void addBet(Bet b) {
		this.bets.add(b);
	}
	
	public void removeBet(Bet b) {
		this.bets.remove(b);
	}

	@Override
	public String toString() {
		return this.prediction + " " + this.factor;

	}

}
