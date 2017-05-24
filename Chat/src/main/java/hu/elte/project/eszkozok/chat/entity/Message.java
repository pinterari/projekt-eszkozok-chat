package hu.elte.project.eszkozok.chat.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <h1>Message</h1>
 * Üzenet entitás a Hibernate ORM kapcsolathoz.
 * 
 * @author Katona Bence
 *
 */
@Entity
@Table(name = "Message")
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "chat_group_id")
	private int chatGroupID;

	@Column(name = "user_id")
	private int userID;

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

	public int getChatGroupID() {
		return chatGroupID;
	}

	public void setChatGroupID(int chatGroup) {
		this.chatGroupID = chatGroup;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int user) {
		this.userID = user;
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
		return "Message [id=" + id + ", chatGroupID=" + chatGroupID + ", userID=" + userID + ", message=" + message
				+ ", sdate=" + sdate + "]";
	}

}
