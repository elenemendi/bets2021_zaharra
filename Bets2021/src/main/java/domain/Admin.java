package domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Admin extends User implements Serializable {
	
	public Admin(){
		
		
	}
	
	public Admin(String name, String surname, String idCard, String email, String username, String password,
			Date birthDate) {
		super(name, surname, idCard, email, username, password, birthDate);

	}

	@Override
	public String toString() {
		return "Administrator: " + this.getUsername();
		
	}
}
