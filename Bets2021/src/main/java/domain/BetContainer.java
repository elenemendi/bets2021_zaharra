package domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD) 
public class BetContainer {
	
	Bet bet;
	Fee fee;
	
	public BetContainer (Bet b) {
		this.bet = b;
		this.fee = b.getFee();
		
	}
	
	public BetContainer() {
		
		
	}
	
	public Fee getFee() {
		return this.fee;
		
	}
	
	public Bet getBet() {
		return this.bet;
		
	}

}
