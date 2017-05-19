package hu.elte.project.eszkozok.chat.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Message")
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "chat_group_id")
	private int chatGroup_id;

	@Column(name = "user_id")
	private int user;

	@Column(name = "message")
	private String message;

	@Column(name = "sdate")
	private Timestamp sdate;

	public Message() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getChatGroup() {
		return chatGroup_id;
	}

	public void setChatGroup(int chatGroup) {
		this.chatGroup_id = chatGroup;
	}

	public int getUser() {
		return user;
	}

	public void setUser(int user) {
		this.user = user;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Timestamp getDate() {
		return sdate;
	}

	public void setDate(Timestamp sqlDate) {
		this.sdate = sqlDate;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", chatGroup=" + chatGroup_id + ", user=" + user + ", message=" + message + ", date="
				+ sdate + "]";
	}

}
