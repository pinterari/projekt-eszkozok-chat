package hu.elte.project.eszkozok.chat.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users_chatgroup")
public class UserChatGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "chat_group_id")
	private int chatGroupID;

	@Column(name = "user_id")
	private int userID;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getChatGroupID() {
		return chatGroupID;
	}

	public void setChatGroupID(int chatGroupID) {
		this.chatGroupID = chatGroupID;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	@Override
	public String toString() {
		return "UserChatGroup [id=" + id + ", chatGroupID=" + chatGroupID + ", userID=" + userID + "]";
	}
	
}
