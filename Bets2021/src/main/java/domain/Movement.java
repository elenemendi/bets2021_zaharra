package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.io.Serializable;
import java.util.Date;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Movement implements Serializable {

	@Id
	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer movNumber;
	private double income;
	private double quantity;
	private String  description;
	@XmlIDREF
	private RegisteredUser user;
	
	
	
	public Movement() {
		super();
		
	}



	public Movement( double income, double quantity, String description,RegisteredUser user) {
		super();
		this.income = income;
		this.quantity = quantity;
		this.description = description;
		this.user=user;
		System.out.println("movement creado");
		
	}



	public int getMovNumber() {
		return movNumber;
	}



	public void setMovNumber(int movNumber) {
		this.movNumber = movNumber;
	}



	public double getIncome() {
		return income;
	}



	public void setIncome(double income) {
		this.income = income;
	}



	public double getQuantity() {
		return quantity;
	}



	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}
	
	public RegisteredUser getUser() {
		return user;
	}

	public void setUser(RegisteredUser user) {
		this.user = user;
	}
	
	
}
