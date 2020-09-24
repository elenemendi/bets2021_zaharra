package domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso ({RegisteredUser.class, Admin.class})
@Entity
public abstract class User implements Serializable {

	private String idNumber;

	private String name;
	private String surname;
	private String email;

	@Id
	@XmlID
	private String username;
	@Id
	@XmlID
	private String password;

	private Date birthDate;

	public User() {

	}

	public User(String name, String surname, String idNumber, String email, String username, String password,
			Date birthDate) {
		super();
		this.name = name;
		this.surname = surname;
		this.idNumber = idNumber;
		this.email = email;
		this.username = username;
		this.password = password;
		this.birthDate = birthDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getidNumber() {
		return idNumber;
	}

	public void setidNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

}
