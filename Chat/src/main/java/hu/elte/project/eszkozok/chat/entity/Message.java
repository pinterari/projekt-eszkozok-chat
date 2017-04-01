package hu.elte.project.eszkozok.chat.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Message")
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ManyToOne
	private ChatGroup chatGroup;

	@ManyToOne
	private User user;

	@Column(name = "message")
	private String message;

	@Column(name = "date")
	private Date date;

	public Message() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ChatGroup getChatGroup() {
		return chatGroup;
	}

	public void setChatGroup(ChatGroup chatGroup) {
		this.chatGroup = chatGroup;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", chatGroup=" + chatGroup + ", user=" + user + ", message=" + message + ", date="
				+ date + "]";
	}

}
