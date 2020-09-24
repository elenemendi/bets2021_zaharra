package domain;

import java.io.Serializable;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Question implements Serializable {

	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@XmlID
	@Id
	@GeneratedValue
	private Integer questionNumber;
	private String question;
	private float betMinimum;

	@XmlIDREF
	private Event event;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Vector<Fee> fees = new Vector<Fee>();

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@XmlIDREF
	private Fee winner;

	private boolean hasWinner = false;
	private double wonMoney;
	private static int numberFees = 0;

	public Question() {
		super();
	}

	public Question(Integer queryNumber, String query, float betMinimum, Event event) {
		super();
		this.questionNumber = queryNumber;
		this.question = query;
		this.betMinimum = betMinimum;
		this.event = event;
		this.fees = new Vector<Fee>();
		this.wonMoney = 0.0;
	}

	public Question(String query, float betMinimum, Event event) {
		super();
		this.question = query;
		this.betMinimum = betMinimum;
		this.fees = new Vector<Fee>();
		this.wonMoney = 0.0;
		this.event = event;
	}

	public Vector<Fee> getFees() {
		return fees;
	}

	public void setFees(Vector<Fee> fees) {
		this.fees = fees;
	}

	/**
	 * Get the number of the question
	 * 
	 * @return the question number
	 */
	public Integer getQuestionNumber() {
		return questionNumber;
	}

	/**
	 * Set the bet number to a question
	 * 
	 * @param questionNumber to be setted
	 */
	public void setQuestionNumber(Integer questionNumber) {
		this.questionNumber = questionNumber;
	}

	/**
	 * Get the question description of the bet
	 * 
	 * @return the bet question
	 */

	public String getQuestion() {
		return question;
	}

	/**
	 * Set the question description of the bet
	 * 
	 * @param question to be setted
	 */
	public void setQuestion(String question) {
		this.question = question;
	}

	/**
	 * Get the minimun ammount of the bet
	 * 
	 * @return the minimum bet ammount
	 */

	public float getBetMinimum() {
		return betMinimum;
	}

	/**
	 * Get the minimun ammount of the bet
	 * 
	 * @param betMinimum minimum bet ammount to be setted
	 */

	public void setBetMinimum(float betMinimum) {
		this.betMinimum = betMinimum;
	}

	/**
	 * Get the result of the query
	 * 
	 * @return the the query result
	 */
	public Fee getResult() {
		return winner;
	}

	/**
	 * Get the result of the query
	 * 
	 * @param result of the query to be setted
	 */

	public void setResult(Fee result) {
		this.winner = result;
		this.hasWinner = true;
	}

	public boolean isHasWinner() {
		return hasWinner;
	}

	/**
	 * Get the event associated to the bet
	 * 
	 * @return the associated event
	 */
	public Event getEvent() {
		return event;
	}

	/**
	 * Set the event associated to the bet
	 * 
	 * @param event to associate to the bet
	 */
	public void setEvent(Event event) {
		this.event = event;
	}
	
	public double getWonMoney() {
		return this.wonMoney;
	}
	
	public void setWonMoney(double d) {
		this.wonMoney = d;
	}

	@Override
	public String toString() {
		return questionNumber + ";" + question + ";" + Float.toString(betMinimum);
	}

	/**
	 * This method creates a fee to a question.
	 * 
	 * @param prediction to be added to the question
	 * @param factor     of that prediction
	 * @return Fee
	 */
	public Fee addFee(Integer id, String prediction, double factor) {
		Fee f = new Fee(id, factor, prediction, this);
		this.fees.add(f);
		return f;
	}

	public Fee addFee(Fee f1) {
		this.fees.add(f1);
		this.numberFees++;
		return f1;

	}

	public boolean DoesFeeExists(String prediction) {
		if (this.getFees() != null)
			for (Fee f : this.getFees()) {
				if (f.getPrediction().compareTo(prediction) == 0)
					return true;
			}
		return false;
	}

	public int getLastFeeNumber() {
		return numberFees;

	}

}