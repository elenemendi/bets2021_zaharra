package domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD) 
public class FeeContainer {
	
	Question question;
	Fee fee;
	
	public FeeContainer (Fee f) {
		this.fee = f;
		this.question = f.getQuestion();
		
	}
	
	public FeeContainer() {
		
		
	}
	
	public Fee getFee() {
		return this.fee;
		
	}
	
	public Question getQuestion() {
		return this.question;
		
	}

}
