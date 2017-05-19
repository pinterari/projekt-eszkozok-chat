package hu.elte.project.eszkozok.chat.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "username")
	private String userName;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email")
	private String email;

	@Column(name = "pwd")
	private String pwd;

	@ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name="users_chatgroup", 
    joinColumns={@JoinColumn(name="user_id", referencedColumnName="id")}, 
    inverseJoinColumns={@JoinColumn(name="chat_group_id", referencedColumnName="id")})
	private Set<ChatGroup> chatGroupSet;

	public User() {
	}

	public User(String userName, String firstName, String lastName, String email, String pwd) {
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.pwd = pwd;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return pwd;
	}

	public void setPassword(String password) {
		this.pwd = password;
	}

	public Set<ChatGroup> getChatGroupSet() {
		return chatGroupSet;
	}

	public void setChatGroupSet(Set<ChatGroup> chatGroupSet) {
		this.chatGroupSet = chatGroupSet;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", password=" + pwd + ", chatGroupSet=" + chatGroupSet + "]";
	}

}
