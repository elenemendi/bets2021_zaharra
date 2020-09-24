package domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD) 
public class QuestionContainer {
	
	Question question;
	Event event;
	
	public QuestionContainer (Question q) {
		this.question = q;
		this.event = q.getEvent();
		
	}
	
	public QuestionContainer() {
		
		
	}
	
	public Question getQuestion() {
		return this.question;
		
	}
	
	public Event getEvent() {
		return this.event;
		
	}

}
